package clienthandlingfacade;

import receivers.TextReceiver;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;

public class ClientDataWriter {
    private final ExecutorService executorService;
    private SocketChannel channel;
    private final TextReceiver receiver = new TextReceiver();


    public ClientDataWriter(ExecutorService fixedThreadPool) {
        executorService = fixedThreadPool;
    }

    public void configureChannel(SocketChannel channel) {
        this.channel = channel;
    }

    public void writeToClient(Serializable objectToWrite) throws IOException {
        receiver.printToLog(this.getClass().getSimpleName(), "Writing to client " + channel.getRemoteAddress() + "...");
        byte[] serializedObject = serialize(objectToWrite);
        sendUsingFixedThreadPool(serializedObject);
        receiver.printToLog(this.getClass().getSimpleName(), "Sent packet to client " + channel.getRemoteAddress());

    }

    private byte[] serialize(Serializable objectToWrite) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(objectToWrite);
        oos.flush();
        return bos.toByteArray();
    }

    private void sendUsingFixedThreadPool(byte[] serializedObject) {
        executorService.execute(() -> {
                    if (serializedObject.length != 0) {
                        writeByBuffer(serializedObject);
                    }
                }

        );
    }

    private void writeByBuffer(byte[] serializedObject) {
        ByteBuffer buffer = ByteBuffer.allocate(4 + serializedObject.length);
        buffer.putInt(serializedObject.length);
        buffer.put(serializedObject);
        buffer.flip();
        while (buffer.hasRemaining()) {
            try {
                channel.write(buffer);
            } catch (IOException e) {
                receiver.printToLog("ERROR:" + this.getClass().getSimpleName(), "Failed to send packet");
            }
        }
    }
}
