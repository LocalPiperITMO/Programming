package app;

import collection.CollectionStorage;
import com.opencsv.exceptions.CsvException;
import converters.CSVToVectorConverter;
import receivers.TextReceiver;
import user.Client;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Main class
 *
 * @author LocalPiper - Sorokin Artem
 */
public class App {

    public static void main(String[] args) throws IOException, CsvException {
        CSVToVectorConverter converter = new CSVToVectorConverter("FILE");
        TextReceiver textReceiver = new TextReceiver();
        try {
            CollectionStorage storage = new CollectionStorage(converter.getVector(), converter.getIdGenerator());
            Client client = new Client(storage);
            client.runningMode();
        } catch (FileNotFoundException fileNotFoundException) {
            textReceiver.printReport("File " + fileNotFoundException.getMessage() + " not found");
        }
    }
}
