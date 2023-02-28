package pattern;


import commands.*;
import exceptions.NoArgumentException;
import validators.UserRequestValidator;

import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

public class Invoker {
    HashMap<String, Command> commandHashMap;
    UserRequestValidator validator;
    Receiver dataSet;
    String argument;
    String commandName;

    public Invoker(Receiver receiver) {
        this.dataSet = receiver;
        this.validator = new UserRequestValidator();
        this.commandHashMap = new HashMap<>();
        commandHashMap.put("help", new HelpCommand(receiver));
        commandHashMap.put("info", new InfoCommand(receiver));
        commandHashMap.put("show", new ShowCommand(receiver));
        commandHashMap.put("remove_by_id", new RemoveByIDCommand(receiver));
        commandHashMap.put("clear", new ClearCommand(receiver));
        commandHashMap.put("save", new SaveCommand(receiver));
        commandHashMap.put("reorder", new ReorderCommand(receiver));
        commandHashMap.put("filter_by_fuel_consumption", new FilterByFuelConsumptionCommand(receiver));
        commandHashMap.put("print_ascending", new PrintAscendingCommand(receiver));
        commandHashMap.put("print_field_ascending_fuel_type", new PrintFieldAscendingFuelTypeCommand(receiver));
    }

    public void getRequestFromUser(String userInput) {
        try {
            argument = "";
            String[] userInputArray = userInput.trim().split(" ", 2);
            commandName = userInputArray[0];
            if (userInputArray.length == 2) {
                argument = userInputArray[1];
            }
            if (Objects.equals(argument, "")) {
                commandHashMap.get(commandName).execute();
            } else {
                commandHashMap.get(commandName).execute(argument);
            }
            System.out.println();
        } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
            validator.emptyRequest();
        } catch (NullPointerException | IOException npe) {
            validator.invalidCommandRequest(userInput.split(" ", 2)[0]);
        } catch (NoArgumentException e) {
            validator.noArgumentCommandRequest(commandHashMap.get(commandName).getClass().getName());
        } catch (NumberFormatException e) {
            validator.illegalArgumentCommandRequest(commandHashMap.get(commandName).getClass().getName(), argument.getClass().getName());
        }
    }
}
