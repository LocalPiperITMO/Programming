package app;

import com.opencsv.exceptions.CsvException;
import converters.CSVtoStringListConverter;
import converters.StringListToObjectVectorConverter;
import datatype.Vehicle;
import pattern.Client;
import pattern.Receiver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.List;
import java.util.Vector;

public class App {
    public static LocalDate creationDate;

    public static void main(String[] args) throws IOException, CsvException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        CSVtoStringListConverter csvToString = new CSVtoStringListConverter(reader);

        List<String[]> text = csvToString.convertCSVtoStringList();
        if (text != null) {
            StringListToObjectVectorConverter stringToVector = new StringListToObjectVectorConverter(text);
            Vector<Vehicle> dataSet = stringToVector.convertStringListToObjectVector();
            creationDate = LocalDate.now();
            System.out.println();

            Receiver receiver = new Receiver(dataSet);
            Client client = new Client(receiver);
            client.runningMode();
        }
    }
}
