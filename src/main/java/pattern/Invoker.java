package pattern;


import commands.*;
import validators.UserRequestValidator;

import java.io.IOException;
import java.util.HashMap;

public class Invoker {
    HashMap<String, Command> commandHashMap;
    UserRequestValidator validator;
    Receiver dataSet;

    public Invoker(Receiver receiver) {
        this.dataSet = receiver;
        this.validator = new UserRequestValidator();
        this.commandHashMap = new HashMap<>();
        commandHashMap.put("help", new HelpCommand(receiver));
        commandHashMap.put("info", new InfoCommand(receiver));
        commandHashMap.put("show", new ShowCommand(receiver));
        commandHashMap.put("reorder",new ReorderCommand(receiver));
        commandHashMap.put("print_ascending", new PrintAscendingCommand(receiver));
        commandHashMap.put("print_field_ascending_fuel_type", new PrintFieldAscendingFuelTypeCommand(receiver));
    }

    public void getRequestFromUser(String userInput) {
        try {
            String[] userInputArray = userInput.split(" ", 2);
            String commandName = userInputArray[0];
            commandHashMap.get(commandName).execute();
            System.out.println();
        } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
            validator.emptyRequest();
        } catch (NullPointerException | IOException npe) {
            validator.invalidCommandRequest(userInput.split(" ", 2)[0]);
        }
    }
}
