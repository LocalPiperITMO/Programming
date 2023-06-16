import collection_handling.CollectionLoader;
import collection_handling.CollectionManager;
import packets.CommandDescriptionPacket;
import receivers.TextReceiver;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.List;

public class CommunicationHandler {
    private Selector selector;
    private SocketChannel activeClient;
    private ServerSocketChannel server;
    private final TextReceiver receiver = new TextReceiver();
    private final int port;


    public CommunicationHandler(int port) throws IOException {
        this.port = port;
        CollectionManager manager = new CollectionManager();
        CollectionLoader loader = new CollectionLoader();
        manager.initCollection(loader.loadCollection("FILE"));

        startServer();
        receiver.printToLog(this.getClass().getSimpleName(), "Opened server at localhost, port " + port);
    }

    private void startServer() throws IOException {
        selector = Selector.open();
        server = ServerSocketChannel.open();
        server.bind(new InetSocketAddress("localhost", port));
        server.configureBlocking(false);
        server.register(selector, SelectionKey.OP_ACCEPT);
    }


    public void startListening() throws IOException {
        receiver.printToLog(this.getClass().getSimpleName(), "Waiting for connections...");

        while (true) {
            selector.select();
            Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();

            while (keyIterator.hasNext()) {
                SelectionKey key = keyIterator.next();
                keyIterator.remove();

                if (key.isAcceptable()) {
                    try {
                        processAcceptableKey(key);
                    } catch (NotSerializableException e) {
                        receiver.printToLog("ERROR", "Can't send packets that contain unserializable elements");
                    }
                } else if (key.isReadable()) {
                    processReadableKey(key);
                }
            }
            if (activeClient == null) {
                server.close();
                selector.close();
                startServer();
            }
        }
    }


    private void processAcceptableKey(SelectionKey key) throws IOException {
        if (activeClient != null) {
            receiver.printToLog(this.getClass().getSimpleName(), "Rejected connection: server is currently busy");
            key.channel().close();
            key.cancel();
            return;
        }

        SocketChannel clientChannel = ((ServerSocketChannel) key.channel()).accept();

        clientChannel.configureBlocking(false);

        activeClient = clientChannel;
        clientChannel.register(selector, SelectionKey.OP_READ);
        receiver.printToLog(this.getClass().getSimpleName(), "Accepted connection from " + clientChannel.getRemoteAddress());
    }

    private void processReadableKey(SelectionKey key) throws IOException {
        SocketChannel clientChannel = (SocketChannel) key.channel();

        PacketHandler packetHandler = new PacketHandler();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        int bytesRead;

        try {
            while ((bytesRead = clientChannel.read(buffer)) > 0) {
                buffer.flip();

                while (buffer.remaining() >= 4) {
                    int dataLength = buffer.getInt();
                    byte[] data = new byte[dataLength];

                    if (buffer.remaining() < dataLength) {
                        buffer.position(buffer.position() - 4);
                        buffer.compact();
                        return;
                    }

                    buffer.get(data);

                    try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
                         ByteArrayInputStream bis = new ByteArrayInputStream(data);
                         ObjectInputStream ois = new ObjectInputStream(bis)) {

                        Object userObject = ois.readObject();

                        if (userObject instanceof CommandDescriptionPacket packet) {
                            receiver.printToLog(this.getClass().getSimpleName(), "Received packet from " + clientChannel.getRemoteAddress());
                            packetHandler.handleUserPacket(packet);
                            sendReportPacket(clientChannel, packetHandler);
                        } else if (userObject instanceof List<?> receivedList) {
                            receiver.printToLog(this.getClass().getSimpleName(), "Received list of packets from " + clientChannel.getRemoteAddress());

                            for (Object object : receivedList) {
                                if (object instanceof CommandDescriptionPacket packet) {
                                    packetHandler.handleUserPacket(packet);
                                }
                            }

                            sendReportPacket(clientChannel, packetHandler);

                        }

                        bos.write(data);
                    } catch (ClassNotFoundException e) {
                        receiver.printToLog("ERROR", "Class casting failed");
                    }
                }

                buffer.compact();
            }


            if (bytesRead == -1) {
                receiver.printToLog(this.getClass().getSimpleName(), "Connection closed by client: " + clientChannel.getRemoteAddress());
                clientChannel.close();
                key.cancel();
                activeClient = null;
            }
        } catch (SocketException e) {
            receiver.printToLog(this.getClass().getSimpleName(), "Connection closed by client: " + clientChannel.getRemoteAddress());
            clientChannel.close();
            key.cancel();
            activeClient = null;
        }
    }

    private void sendReportPacket(SocketChannel clientChannel, PacketHandler packetHandler) throws IOException {
        List<Serializable> reportPacket = packetHandler.getListOfPackets();
        receiver.printToLog(this.getClass().getSimpleName(), "Sending report...");

        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(bos)) {

            oos.writeObject(reportPacket);
            byte[] reportBytes = bos.toByteArray();
            ByteBuffer reportBuffer = ByteBuffer.allocate(reportBytes.length + 4);
            reportBuffer.putInt(reportBytes.length);
            reportBuffer.put(reportBytes);
            reportBuffer.flip();

            while (reportBuffer.hasRemaining()) {
                clientChannel.write(reportBuffer);
            }

            reportBuffer.clear();
            receiver.printToLog(this.getClass().getSimpleName(), "Report sent");
        }
    }
}
