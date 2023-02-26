package converters;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

public class CSVtoStringListConverter {
    private final BufferedReader reader;

    public CSVtoStringListConverter(BufferedReader reader) {
        this.reader = reader;
    }

    public List<String[]> convertCSVtoStringList() throws IOException, CsvException {
        InputStreamReader readDataFromFile = getVariableNameFromUser(reader);
        if (readDataFromFile == null) {
            return null;
        }
        CSVReader csvReader = new CSVReader(readDataFromFile);
        return csvReader.readAll();
    }

    public InputStreamReader getVariableNameFromUser(BufferedReader reader) throws IOException {
        boolean collectionCreationForbidden = false;
        InputStreamReader readDataFromFile = null;
        while (!collectionCreationForbidden) {
            System.out.println("Input variable name:\n(NOTE: Type \"exit\" to leave the program).");
            String userInput = reader.readLine();
            if (userInput == null || checkIfUserLeaves(userInput)) {
                System.out.println("Leaving the program");
                break;
            }
            String varAddress = System.getenv(userInput);
            try {
                readDataFromFile = new InputStreamReader(Files.newInputStream(Paths.get(varAddress)), StandardCharsets.UTF_8);
                collectionCreationForbidden = true;
            } catch (NullPointerException npe) {
                readDataFromFile = handleNullName(reader);
                collectionCreationForbidden = true;
            }
        }
        return (collectionCreationForbidden) ? readDataFromFile : null;
    }

    public InputStreamReader handleNullName(BufferedReader reader) throws IOException {
        while (true) {
            System.out.println("""
                    Environment variable not found. Choose from 2 options:
                    1. Try again.
                    2. Create empty file.
                    Print either 1 or 2 into the console.""");
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
                return new InputStreamReader(Files.newInputStream(empty_file.toPath()), StandardCharsets.UTF_8);
            } else {
                System.out.println("Wrong input!");
            }
        }
    }

    public boolean checkIfUserLeaves(String userInput) {
        return (Objects.equals(userInput.strip().toLowerCase(), "exit"));
    }
}

