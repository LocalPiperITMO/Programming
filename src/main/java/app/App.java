package app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class App {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        CSVtoStringConverter csvToString = new CSVtoStringConverter(reader);
        String text = csvToString.convertCSVtoString();
        System.out.println(text);
    }
}
