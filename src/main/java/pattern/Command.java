package pattern;

import exceptions.NoArgumentException;

import java.io.IOException;

public interface Command {
    default boolean checkIfUserInputMatchesRequiredArgument(String userInput, boolean isArgumentRequired) {
        return userInput.trim().length() != 0 || !isArgumentRequired;
    }

    void execute(String arg) throws IOException, NoArgumentException;
}
