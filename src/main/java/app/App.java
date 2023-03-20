package app;

import com.opencsv.exceptions.CsvException;
import user.Client;
import collection.CollectionStorage;
import converters.CSVToVectorConverter;

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
        try {
            CollectionStorage storage = new CollectionStorage(converter.getVector(), converter.getIdGenerator());
            Client client = new Client(storage);
            client.runningMode();
        } catch (FileNotFoundException fileNotFoundException) {
            System.out.println("File " + fileNotFoundException.getMessage() + " not found");
        }
    }
}
