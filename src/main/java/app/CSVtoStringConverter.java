package app;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class CSVtoStringConverter {
    private final BufferedReader reader;

    public CSVtoStringConverter(BufferedReader reader) {
        this.reader = reader;
    }

    public String convertCSVtoString() throws IOException {
        // read file from env.variable
        InputStreamReader readDataFromFile = getVariableNameFromUser(reader);
        if (readDataFromFile == null) {
            return null;
        }
        StringBuilder text = new StringBuilder();
        int currentChar = readDataFromFile.read();
        while (currentChar != -1) {
            text.append((char) currentChar);
            currentChar = readDataFromFile.read();
        }
        return String.valueOf(text);
    }

    public InputStreamReader getVariableNameFromUser(BufferedReader reader) throws IOException {
        boolean collectionCreationState = true;
        InputStreamReader readDataFromFile = null;
        while (collectionCreationState) {
            System.out.println("""
                    Input variable name:
                    (NOTE: Type "exit" to leave the program).
                    """);
            String userInput = reader.readLine();
            if (userInput == null || checkIfUserLeaves(userInput)) {
                System.out.println("Leaving the program");
                break;
            }
            String varAddress = System.getenv(userInput);
            try {
                readDataFromFile = new InputStreamReader(new FileInputStream(varAddress), StandardCharsets.UTF_8);
                collectionCreationState = false;
            } catch (NullPointerException npe) {
                readDataFromFile = handleNullName(reader);
                collectionCreationState = false;
            }
        }
        return (collectionCreationState) ? null : readDataFromFile;
    }

    public InputStreamReader handleNullName(BufferedReader reader) throws IOException {
        while (true) {
            System.out.println("""
                    Environment variable not found. Choose from 2 options:
                    1. Try again.
                    2. Create empty file.
                    Print either 1 or 2 into the console.
                    """);
            String userRequest = reader.readLine();
            if (userRequest == null || checkIfUserLeaves(userRequest.strip())) {
                System.out.println("Leaving the program");
                return null;
            }
            if (Objects.equals(userRequest.strip(), "1")) {
                return getVariableNameFromUser(reader);
            } else if (Objects.equals(userRequest.strip(), "2")) {
                File empty_file = new File("C:\\Users\\Honor\\IdeaProjects\\App\\empty_file.csv");
                if (empty_file.createNewFile()) {
                    System.out.println("Empty file created successfully.");
                } else {
                    System.out.println("Empty file already exists");
                }
                return new InputStreamReader(new FileInputStream(empty_file), StandardCharsets.UTF_8);
            } else {
                System.out.println("Wrong input!");
            }
        }
    }

    public boolean checkIfUserLeaves(String userInput) {
        return (Objects.equals(userInput.strip().toLowerCase(), "exit"));
    }
}
