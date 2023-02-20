package app;

import java.util.Objects;

public class ExitCheck {
    private String userInput;

    public static boolean checkIfUserLeaves(String userInput) {
        return (Objects.equals(userInput.strip().toLowerCase(), "exit"));
    }
}
