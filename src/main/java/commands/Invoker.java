package commands;


import exceptions.InvalidCommandNameException;
import exceptions.NoArgumentException;

import java.io.IOException;
import java.util.HashMap;

public class Invoker {
    private final HashMap<String, Command> commandHashMap;
    private String argument;
    private String commandName;

    public Invoker(Receiver receiver) {
        this.commandHashMap = new HashMap<>();
        commandHashMap.put("help", new HelpCommand(commandHashMap));
        commandHashMap.put("info", new InfoCommand(receiver));
        commandHashMap.put("show", new ShowCommand(receiver));
        commandHashMap.put("add", new AddElementCommand(receiver));
        commandHashMap.put("update", new UpdateElementCommand(receiver));
        commandHashMap.put("remove_by_id", new RemoveByIDCommand(receiver));
        commandHashMap.put("clear", new ClearCommand(receiver));
        commandHashMap.put("save", new SaveCommand(receiver));
        commandHashMap.put("add_if_max", new AddIfMaxElementCommand(receiver));
        commandHashMap.put("remove_greater", new RemoveGreaterElementsCommand(receiver));
        commandHashMap.put("reorder", new ReorderCommand(receiver));
        commandHashMap.put("filter_by_fuel_consumption", new FilterByFuelConsumptionCommand(receiver));
        commandHashMap.put("print_ascending", new PrintAscendingCommand(receiver));
        commandHashMap.put("print_field_ascending_fuel_type", new PrintFieldAscendingFuelTypeCommand(receiver));
    }

    public void getRequestFromUser(String userInput) {
        try {
            // filtering user request, getting command name and arguments, executing the command
            argument = "";
            String[] userInputArray = userInput.trim().split(" ", 2);
            commandName = userInputArray[0];
            if (userInputArray.length == 2) {
                argument = userInputArray[1];
            }
            if (commandHashMap.get(commandName) != null) {
                commandHashMap.get(commandName).execute(argument);
            } else {
                throw new InvalidCommandNameException();
            }
            System.out.println();
        } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
            System.out.println("Empty request. Try again");
        } catch (NoArgumentException e) {
            System.out.println(commandName + " requires an argument: none were given");
        } catch (NumberFormatException e) {
            System.out.println(commandName + " requires a different argument type, but " + argument.getClass().getSimpleName() + " was given");
        } catch (InvalidCommandNameException e) {
            System.out.println("There is no command named \"" + commandName + "\". Try again");
        } catch (IOException e) {
            System.out.println("Error!");
        }
    }
}
