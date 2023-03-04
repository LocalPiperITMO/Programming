package app;

import com.opencsv.exceptions.CsvException;
import commands.Client;
import commands.Receiver;
import converters.CSVtoStringListConverter;
import converters.StringListToObjectVectorConverter;
import datatype.Vehicle;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Vector;

public class App {

    public static void main(String[] args) throws IOException, CsvException {
        CSVtoStringListConverter csvToString = new CSVtoStringListConverter();
        try {
            List<String[]> text = csvToString.convertCSVtoStringList();
            if (text != null) {
                StringListToObjectVectorConverter stringToVector = new StringListToObjectVectorConverter(text);
                Vector<Vehicle> dataSet = stringToVector.convertStringListToObjectVector();
                System.out.println();

                Receiver receiver = new Receiver(dataSet, csvToString.getVarAddress(), stringToVector.getVehicleFactory());
                Client client = new Client(receiver);
                client.runningMode();
            }
        } catch (FileNotFoundException fileNotFoundException) {
            System.out.println("File " + fileNotFoundException.getMessage() + " not found");
        }
    }
}
