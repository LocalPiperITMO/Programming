package app;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class GetVariableName {
    public static InputStreamReader getVariableNameFromUser(BufferedReader reader) throws IOException {
        boolean collectionCreationState = true;
        InputStreamReader readDataFromFile = null;
        while (collectionCreationState) {
            System.out.println("""
                    Input variable name:
                    (NOTE: Type "exit" to leave the program).
                    """);
            String userInput = reader.readLine();
            if (userInput == null || ExitCheck.checkIfUserLeaves(userInput)) {
                System.out.println("Leaving the program");
                break;
            }
            String varAddress = System.getenv(userInput);
            try {
                readDataFromFile = new InputStreamReader(new FileInputStream(varAddress), StandardCharsets.UTF_8);
                collectionCreationState = false;
            } catch (NullPointerException npe) {
                readDataFromFile = VariableNameNullHandler.handleNullName(reader);
                collectionCreationState = false;
            }
        }
        return (collectionCreationState) ? null : readDataFromFile;
    }
}
