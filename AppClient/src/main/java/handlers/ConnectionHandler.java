package handlers;

import exceptions.UserDisconnectException;
import output.TextReceiver;
import packets.CollectionPacket;
import packets.ReportPacket;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.List;

public class ConnectionHandler {
    private final TextReceiver receiver = new TextReceiver();


    public void startConnection(int port) throws UserDisconnectException, IOException {
        try (SocketChannel channel = SocketChannel.open()) {
            channel.configureBlocking(false);
            channel.connect(new InetSocketAddress("localhost", port));
            Selector selector = Selector.open();
            channel.register(selector, SelectionKey.OP_CONNECT);

            while (true) {
                int readyChannels = selector.select();
                if (readyChannels == 0) {
                    continue;
                }
                Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
                while (keyIterator.hasNext()) {
                    SelectionKey key = keyIterator.next();
                    if (key.isConnectable()) {
                        handleConnectable(key, channel);
                    }
                    if (key.isReadable()) {
                        handleReadable(key);
                    }
                    try {
                        if (key.isWritable()) {
                            handleWritable(key);
                        }
                    } catch (NotSerializableException | ClassCastException e) {
                        System.out.println("Can't send packet to server: it contains unserializable elements");
                    }
                    keyIterator.remove();
                }
            }
        }

    }

    private void handleConnectable(SelectionKey key, SocketChannel channel) throws UserDisconnectException, IOException {
        try {
            if (channel.finishConnect()) {
                if (channel.read(ByteBuffer.allocate(1)) >= 0) {
                    receiver.print("Connection established");
                    key.interestOps(SelectionKey.OP_WRITE);
                } else {
                    receiver.print("Server is not responding");
                    throw new UserDisconnectException();
                }
            }
        } catch (SocketException e) {
            if (e.getMessage().contains("Connection reset") || e.getMessage().contains("Connection refused")) {
                receiver.print("""
                        Couldn't connect to server
                        If you see this message, try the following:
                        1. Check the port number, perhaps the server is listening on a different port than you provided
                        2. If that's not the case, the server might be busy with another client. Try connecting later
                        """);
            }
            throw new UserDisconnectException();
        }
    }


    private void handleReadable(SelectionKey key) throws IOException, UserDisconnectException {
        SocketChannel channel = (SocketChannel) key.channel();
        ByteBuffer sizeBuffer = ByteBuffer.allocate(Integer.BYTES);

        while (sizeBuffer.hasRemaining()) {
            int bytesRead = channel.read(sizeBuffer);
            if (bytesRead == -1) {
                return;
            }
        }

        sizeBuffer.flip();

        int reportSize = sizeBuffer.getInt();
        ByteBuffer reportBuffer = ByteBuffer.allocate(reportSize);

        while (reportBuffer.hasRemaining()) {
            int bytesRead = channel.read(reportBuffer);
            if (bytesRead == -1) {
                return;
            }
        }

        reportBuffer.flip();

        ByteArrayInputStream bis = new ByteArrayInputStream(reportBuffer.array());
        ObjectInputStream ois = new ObjectInputStream(bis);
        try {
            Object userObject = ois.readObject();
            if (userObject instanceof List<?> receivedList) {
                for (Object object : receivedList) {
                    if (object instanceof ReportPacket packet) {
                        receiver.print(packet.getReport());
                        if (packet.getReport().equalsIgnoreCase("Goodbye")) {
                            throw new UserDisconnectException();
                        }
                    } else if (object instanceof CollectionPacket<?> collectionPacket) {
                        receiver.printCollection(collectionPacket.getPacketIdentifier(), collectionPacket.getCollection());
                    }
                }
            } else if (userObject instanceof ReportPacket singlePacket) {
                receiver.print(singlePacket.getReport());
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Well, shit");
        }
        key.interestOps(SelectionKey.OP_WRITE);
    }


    private void handleWritable(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        UserInputHandler handler = new UserInputHandler();
        handler.greetUser();

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(handler.handleInput());


        byte[] packetBytes = bos.toByteArray();

        ByteBuffer buffer = ByteBuffer.allocate(packetBytes.length + 4);
        buffer.putInt(packetBytes.length);
        buffer.put(packetBytes);
        buffer.flip();

        while (buffer.hasRemaining()) {
            channel.write(buffer);
        }
        buffer.clear();
        key.interestOps(SelectionKey.OP_READ);
    }
}

