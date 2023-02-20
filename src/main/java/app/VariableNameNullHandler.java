package app;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class VariableNameNullHandler {
    public static InputStreamReader handleNullName(BufferedReader reader) throws IOException {
        while (true) {
            System.out.println("""
                    Environment variable not found. Choose from 2 options:
                    1. Try again.
                    2. Create empty file.
                    Print either 1 or 2 into the console.
                    """);
            String userRequest = reader.readLine();
            if (userRequest == null || ExitCheck.checkIfUserLeaves(userRequest.strip())) {
                System.out.println("Leaving the program");
                return null;
            }
            if (Objects.equals(userRequest.strip(), "1")) {
                return GetVariableName.getVariableNameFromUser(reader);
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
}
