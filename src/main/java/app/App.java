package app;

import collection.CollectionStorage;
import converters.CSVToVectorConverter;
import exceptions.InvalidPathException;
import receivers.TextReceiver;
import user.Client;

import java.io.IOException;

/**
 * Main class of this program. Starts the console application
 *
 * @author LocalPiper - Sorokin Artem
 */
public class App {
    /**
     * Creates text receiver for output purposes in case something goes wrong<br>
     * Creates converter class and collection storage class, gets collection prepared<br>
     * Then creates client class and calls its function, which starts the application<br>
     * If something goes wrong with parsing file/user decides to leave, closes session
     */
    public static void main(String[] args) {
        TextReceiver receiver = new TextReceiver();
        try {
            CSVToVectorConverter converter = new CSVToVectorConverter();
            CollectionStorage storage = new CollectionStorage(converter.getVector(), converter.getIdGenerator());
            Client client = new Client(storage);
            client.runningMode();
        } catch (InvalidPathException | IOException hardException) {
            receiver.print("Leaving the program");
        }
    }
}
