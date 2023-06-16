package validation;

import java.util.HashMap;
import java.util.Objects;

public class NameValidationStrategy implements CommandValidationStrategy {
    private final HashMap<String, String[]> commandLibrary;

    public NameValidationStrategy(HashMap<String, String[]> commandLibrary) {
        this.commandLibrary = commandLibrary;
    }

    @Override
    public ValidationReport validate(String commandName, String commandArgument) {
        if (commandLibrary.containsKey(commandName)) {
            return ValidationReport.OK;
        }
        if (Objects.equals(commandName, "execute_script")) {
            return ValidationReport.SCRIPT_DETECTED;
        }
        return ValidationReport.COMMAND_DOES_NOT_EXIST;
    }
}
