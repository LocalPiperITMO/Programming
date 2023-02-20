package app;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class App {
    public static void main(String[] args) throws IOException {
        InputStreamReader readDataFromFile = null;
        exit:
        while (true) {
            // read file from env.variable
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            boolean collectionCreationState = true;
            while (collectionCreationState) {
                System.out.println("""
                        Input variable name:
                        (NOTE: Type "exit" to leave the program).
                        """);
                String userInput = reader.readLine();
                if (ExitCheck.checkIfUserLeaves(userInput)) {
                    break exit;
                }
                String varAddress = System.getenv(userInput);
                try {
                    readDataFromFile = new InputStreamReader(new FileInputStream(varAddress), StandardCharsets.UTF_8);
                    collectionCreationState = false;
                } catch (NullPointerException npe) {
                    boolean errorHandlingState = true;
                    while (errorHandlingState) {
                        System.out.println("""
                                Environment variable not found. Choose from 2 options:
                                1. Try again.
                                2. Create empty file.
                                Print either 1 or 2 into the console.
                                """);
                        String userRequest = reader.readLine().strip();
                        if (ExitCheck.checkIfUserLeaves(userRequest)) {
                            break exit;
                        }
                        if (Objects.equals(userRequest, "1")) {
                            errorHandlingState = false;
                        } else if (Objects.equals(userRequest, "2")) {
                            File empty_file = new File("empty_file.csv");
                            readDataFromFile = new InputStreamReader(new FileInputStream(empty_file), StandardCharsets.UTF_8);
                            errorHandlingState = false;
                        } else {
                            System.out.println("Wrong input!");
                        }
                    }
                }
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
