package app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class App {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        InputStreamReader readDataFromFile;
        while (true) {
            // read file from env.variable
            readDataFromFile = GetVariableName.getVariableNameFromUser(reader);
            if (readDataFromFile == null) {
                break;
            }
            StringBuilder text = new StringBuilder();
            int currentChar = readDataFromFile.read();
            while (currentChar != -1) {
                text.append((char) currentChar);
                currentChar = readDataFromFile.read();
            }
            System.out.println(text);
        }
    }
}
