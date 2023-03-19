package app;

import com.opencsv.exceptions.CsvException;
import commands.Client;
import commands.Receiver;
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
            Receiver receiver = new Receiver(converter.getVector(), converter.getVarPath(), converter.getIdGenerator());
            Client client = new Client(receiver);
            client.runningMode();
        } catch (FileNotFoundException fileNotFoundException) {
            System.out.println("File " + fileNotFoundException.getMessage() + " not found");
        }
    }
}
