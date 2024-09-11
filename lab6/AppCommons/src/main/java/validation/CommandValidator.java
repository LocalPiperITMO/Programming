package validation;

import commands.CommandLibrary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class CommandValidator {
    private final List<CommandValidationStrategy> strategies = new ArrayList<>();

    public CommandValidator() {
        HashMap<String, String[]> commandLibrary = new CommandLibrary().getCommandLibrary();
        strategies.add(new NameValidationStrategy(commandLibrary));
        strategies.add(new ArgumentValidationStrategy(commandLibrary));
        strategies.add(new ScriptValidationStrategy());
    }

    public ValidationReport checkCommand(String userInput) {
        String[] slicedCommand = sliceCommand(userInput);
        ValidationReport nameValidationReport = strategies.get(0).validate(slicedCommand[0], slicedCommand[1]);
        if (nameValidationReport.equals(ValidationReport.OK)) {
            return strategies.get(1).validate(slicedCommand[0], slicedCommand[1]);
        }
        if (nameValidationReport.equals(ValidationReport.SCRIPT_DETECTED)) {
            return strategies.get(2).validate(slicedCommand[0], slicedCommand[1]);
        }
        return nameValidationReport;
    }

    public String[] sliceCommand(String command) {
        String commandName = command.split(" ", 2)[0].trim();
        String commandArgument = "";
        if (command.split(" ", 2).length == 2) {
            commandArgument = command.split(" ", 2)[1].trim();
        }
        return new String[]{commandName, commandArgument};
    }
}
