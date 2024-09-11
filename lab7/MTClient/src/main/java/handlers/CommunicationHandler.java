package handlers;

import exceptions.UserDisconnectException;
import uih_utils.TextReceiver;
import wrapping.*;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CommunicationHandler {
    private final int port;
    private SocketChannel channel;
    private final AuthorizationPacket authorizationPacket;
    public static CommandInstructionsPacket CIP = new CommandInstructionsPacket(new HashMap<>());
    private final TextReceiver receiver = new TextReceiver();
    private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

    public CommunicationHandler(int port, String name, String password, boolean regFlag) {
        this.port = port;
        this.authorizationPacket = new PacketWrapper().wrapAP(name, password, regFlag);
    }

    public void startConnection() throws UserDisconnectException {
        connectToServer();
        executorService.scheduleAtFixedRate(this::writeToServer, 0, 1, TimeUnit.SECONDS);

    }

    private void connectToServer() throws UserDisconnectException {

        int attempts = 0;
        boolean connected = false;

        receiver.print("Trying to connect to server for 15 seconds...");
        while (!connected && attempts < 15) {
            try {
                channel = SocketChannel.open(new InetSocketAddress("localhost", port));
                connected = true;
            } catch (IOException e) {
                attempts++;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    // Ignore interrupt
                }
            }
        }
        receiver.print("Connection successful");
        if (!connected) {
            receiver.print("Couldn't connect to server, leaving...");
            throw new UserDisconnectException();
        }
    }

    private void writeToServer() {

        try {
            if (CIP.getCommandHashMap().size() == 0) {
                sendPacketToServer(authorizationPacket);
            } else {
                UserInputHandler handler = new UserInputHandler(authorizationPacket);
                handler.greetUser();
                sendPacketToServer(handler.handleInput());
            }
            readFromServer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void readFromServer() {

        Optional<Object> rawPacket = getPacketFromServer();
        if (rawPacket.isPresent()) {
            if (rawPacket.get() instanceof List<?> receivedList) {
                for (Object object : receivedList) {
                    if (object instanceof ReportPacket packet) {
                        receiver.print(packet.getReport());
                        if (packet.getReport().equalsIgnoreCase("Goodbye!")) {
                            executorService.shutdown();
                        }
                    } else if (object instanceof CollectionPacket<?> collectionPacket) {
                        receiver.printCollection(collectionPacket.getPacketIdentifier(), collectionPacket.getCollection());
                    }
                }

            } else if (rawPacket.get() instanceof ReportPacket singlePacket) {
                receiver.print(singlePacket.getReport());
            } else if (rawPacket.get() instanceof CommandInstructionsPacket cip) {
                if (cip.getCommandHashMap().size() == 0) {
                    if (authorizationPacket.getRegFlag()) {
                        receiver.print("Failed to register: username is already in use");
                        executorService.shutdown();
                    } else {
                        receiver.print("Invalid username/password");
                        executorService.shutdown();
                    }
                } else {
                    CIP = cip;
                    receiver.print("You can start working now");
                }
            }
        }
    }

    private void sendPacketToServer(Serializable packet) {

        try (ByteArrayOutputStream bos = new ByteArrayOutputStream(); ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeObject(packet);

            byte[] packetBytes = bos.toByteArray();

            ByteBuffer buffer = ByteBuffer.allocate(packetBytes.length + 4);
            buffer.putInt(packetBytes.length);
            buffer.put(packetBytes);
            buffer.flip();

            while (buffer.hasRemaining()) {
                channel.write(buffer);
            }

        } catch (IOException e) {
            receiver.print("Error writing to server: server down");
            executorService.shutdownNow();
        }
    }

    private Optional<Object> getPacketFromServer() {
        try {
            ByteBuffer sizeBuffer = ByteBuffer.allocate(Integer.BYTES);
            while (sizeBuffer.hasRemaining()) {
                int bytesRead = channel.read(sizeBuffer);
                if (bytesRead == -1) {
                    return Optional.empty();
                }
            }
            sizeBuffer.flip();

            int reportSize = sizeBuffer.getInt();
            ByteBuffer reportBuffer = ByteBuffer.allocate(reportSize);

            while (reportBuffer.hasRemaining()) {
                int bytesRead = channel.read(reportBuffer);
                if (bytesRead == -1) {
                    return Optional.empty();
                }
            }
            reportBuffer.flip();

            ByteArrayInputStream bis = new ByteArrayInputStream(reportBuffer.array());
            try (ObjectInputStream ois = new ObjectInputStream(bis)) {
                return Optional.of(ois.readObject());
            } catch (ClassNotFoundException e) {
                System.out.println("Well, shit");
            }
        } catch (IOException e) {
            System.out.println("ERROR");
        }
        return Optional.empty();
    }
}
