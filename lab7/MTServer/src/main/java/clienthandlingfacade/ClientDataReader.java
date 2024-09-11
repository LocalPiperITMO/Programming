package clienthandlingfacade;

import receivers.TextReceiver;
import tasks.ReadingTask;
import wrapping.AuthorizationPacket;
import wrapping.CommandDescriptionPacket;
import wrapping.PacketWrapper;

import java.io.EOFException;
import java.io.IOException;
import java.io.Serializable;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;

import static clienthandling.ClientHandler.CIP;

public class ClientDataReader {
    private final TextReceiver receiver = new TextReceiver();
    private final ClientAuthorizer clientAuthorizer = new ClientAuthorizer();
    private final ClientDataWriter clientDataWriter;
    private final ClientDataProcessor clientDataProcessor;
    private final ForkJoinPool forkJoinPool;

    public ClientDataReader(ForkJoinPool requestPool, ForkJoinPool processingPool, ExecutorService responsePool) {
        clientDataWriter = new ClientDataWriter(responsePool);
        clientDataProcessor = new ClientDataProcessor(processingPool);
        forkJoinPool = requestPool;
    }

    public void readDataFromClient(SelectionKey key) {
        try {
            SocketChannel clientChannel = (SocketChannel) key.channel();
            receiver.printToLog(this.getClass().getSimpleName(), "Reading data from " + clientChannel.getRemoteAddress() + "...");
            byte[] clientData = readByteStreamFromChannel(clientChannel);
            clientDataWriter.configureChannel(clientChannel);
            receiver.printToLog(this.getClass().getSimpleName(), "Finished reading data from " + clientChannel.getRemoteAddress());
            Optional<AuthorizationPacket> authorizationPacketOptional = clientAuthorizer.checkPacket(clientData);
            if (authorizationPacketOptional.isPresent()) {
                if (clientAuthorizer.authorizeClient(authorizationPacketOptional.get())) {
                    clientDataWriter.writeToClient(CIP);
                } else {
                    clientDataWriter.writeToClient(new PacketWrapper().wrapCIP(new HashMap<>()));
                }
            } else {
                receiver.printToLog(this.getClass().getSimpleName(), "Received list of packets from " + clientChannel.getRemoteAddress());
                List<Serializable> resultList = clientDataProcessor.processUserPackets(readUsingFork(clientData));
                receiver.printToLog(this.getClass().getSimpleName(), "Processing finished");
                clientDataWriter.writeToClient((Serializable) resultList);
            }
        } catch (Exception e) {
            handleException(e, key);
        }

    }

    private byte[] readByteStreamFromChannel(SocketChannel channel) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(4096);
        byte[] data = new byte[0];

        while (channel.read(buffer) > 0) {
            buffer.flip();
            data = prepareByteArrayWithData(buffer, data);
            if (buffer.remaining() < 4) {
                buffer.compact();
            } else {
                int dataLength = buffer.getInt();
                buffer = ByteBuffer.allocate(dataLength);
            }
        }
        return data;
    }

    private byte[] prepareByteArrayWithData(ByteBuffer buffer, byte[] data) {
        while (buffer.remaining() >= 4) {
            int dataLength = buffer.getInt();
            byte[] newData = new byte[data.length + dataLength];

            if (buffer.remaining() < dataLength) {
                buffer.position(buffer.position() - 4);
                buffer.compact();
                return new byte[0];
            }
            buffer.get(newData, data.length, dataLength);
            System.arraycopy(data, 0, newData, 0, data.length);
            data = newData;
        }
        return data;
    }

    private List<CommandDescriptionPacket> readUsingFork(byte[] clientData) {
        ReadingTask readingTask = new ReadingTask(clientData, 0, clientData.length);
        return forkJoinPool.invoke(readingTask);

    }

    private void handleException(Exception exception, SelectionKey key) {
        if (exception instanceof SocketException || exception instanceof EOFException) {
            receiver.printToLog(this.getClass().getSimpleName(), "Client disconnected");
        } else {
            receiver.printToLog("ERROR:" + this.getClass().getSimpleName(), "An exception occurred: " + exception.getClass().getSimpleName());
            receiver.printToLog("ERROR:" + this.getClass().getSimpleName(), "Reason: " + exception.getMessage());
            StackTraceElement[] stackTrace = exception.getStackTrace();
            String methodName = "";
            for (StackTraceElement stackTraceElement : stackTrace) {
                if (!stackTraceElement.getClassName().startsWith("java.") && !stackTraceElement.getClassName().startsWith("javax.")) {
                    methodName = stackTraceElement.getMethodName();
                    break;
                }
            }
            receiver.printToLog("ERROR:" + this.getClass().getSimpleName(), "Occurred in method: " + methodName);
        }
        key.cancel();
    }

}
