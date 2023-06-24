package clienthandlingfacade;

import receivers.TextReceiver;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class ClientAcceptor {
    private final TextReceiver receiver = new TextReceiver();

    public void acceptClient(Selector selector, SelectionKey key) {
        try {
            receiver.printToLog(this.getClass().getSimpleName(), "Accepting new connection...");
            ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
            SocketChannel clientChannel = serverChannel.accept();
            clientChannel.configureBlocking(false);
            receiver.printToLog(this.getClass().getSimpleName(), "Accepted connection from " + clientChannel.getRemoteAddress());
            clientChannel.register(selector, SelectionKey.OP_READ);
            receiver.printToLog(this.getClass().getSimpleName(), "Channel " + clientChannel.getRemoteAddress() + " is registered for: " + SelectionKey.OP_READ);
        } catch (IOException e) {
            receiver.printToLog("ERROR:" + this.getClass().getSimpleName(), "An exception occurred: " + e.getClass().getSimpleName());
            receiver.printToLog("ERROR:" + this.getClass().getSimpleName(), "Reason: " + e.getMessage());
            key.cancel();
        }
    }
}
