package clienthandlingfacade;

import receivers.TextReceiver;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

public class ClientOperationSelector {
    private final Selector selector;
    private final ClientAcceptor clientAcceptor;
    private final ClientDataReader clientDataReader;

    public ClientOperationSelector(int port) throws IOException {
        TextReceiver receiver = new TextReceiver();
        receiver.printToLog(this.getClass().getSimpleName(), "Starting server...");
        selector = Selector.open();
        ServerSocketChannel server = ServerSocketChannel.open();
        server.bind(new InetSocketAddress("localhost", port));
        server.configureBlocking(false);
        server.register(selector, SelectionKey.OP_ACCEPT);

        receiver.printToLog(this.getClass().getSimpleName(), "Creating pools...");
        int MAX_THREADS = 10;
        ForkJoinPool requestPool = new ForkJoinPool();
        ForkJoinPool processingPool = new ForkJoinPool();
        ExecutorService responsePool = Executors.newFixedThreadPool(MAX_THREADS);
        receiver.printToLog(this.getClass().getSimpleName(), "Pools created");

        receiver.printToLog(this.getClass().getSimpleName(), "Server is running at port " + port);
        this.clientAcceptor = new ClientAcceptor();
        this.clientDataReader = new ClientDataReader(requestPool, processingPool, responsePool);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                requestPool.shutdown();
                processingPool.shutdown();
                responsePool.shutdown();
                selector.close();
                server.close();
                receiver.printToLog(this.getClass().getSimpleName(), "Server shutdown finished");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));
    }

    public void beginSelectionLoop() throws IOException {
        while (selector.isOpen()) {
            int numKeys = selector.select(500);
            if (numKeys == 0) {
                continue;
            }

            Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();

            while (keyIterator.hasNext()) {
                SelectionKey key = keyIterator.next();
                if (key.isAcceptable()) {
                    clientAcceptor.acceptClient(selector, key);
                } else if (key.isReadable()) {
                    clientDataReader.readDataFromClient(key);
                }
                keyIterator.remove();
            }
        }
    }
}
