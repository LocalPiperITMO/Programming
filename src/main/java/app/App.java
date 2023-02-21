package app;

import com.opencsv.exceptions.CsvException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class App {

    public static void main(String[] args) throws IOException, CsvException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        CSVtoStringListConverter csvToString = new CSVtoStringListConverter(reader);
        List<String[]> text = csvToString.convertCSVtoStringList();
        System.out.println(text);
    }
}
