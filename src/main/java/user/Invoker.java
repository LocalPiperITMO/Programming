package user;


import collection.CollectionStorage;
import commands.*;
import exceptions.InvalidArgumentsWhileVehicleBuildingViaScriptException;
import exceptions.InvalidCommandNameException;
import exceptions.NoArgumentException;
import receivers.*;

import java.io.IOException;
import java.util.HashMap;
/**
 * Invoker class
 * Used for processing user inputs and sending commands
 * Stores commandHashMap used for accessing commands
 */
public class Invoker {
    /**
     * Contains every command
     */
    private final HashMap<String, Command> commandHashMap;
    /**
     * User argument (if the command requires one)
     */
    private String argument;
    /**
     * User-inputted command name
     */
    private String commandName;
    /**
     * Enables output stream
     */
    private final TextReceiver textReceiver;
    /**
     * Sent to ExecuteScriptCommandReceiver
     */
    private final BuilderCommandReceiver builderCommandReceiver;


    public Invoker(CollectionStorage storage) {
        this.commandHashMap = new HashMap<>();
        DisplayingCommandReceiver displayingCommandReceiver = new DisplayingCommandReceiver(storage, this);
        SortingCommandReceiver sortingCommandReceiver = new SortingCommandReceiver(storage);
        CollectionProcessingCommandReceiver collectionProcessingCommandReceiver = new CollectionProcessingCommandReceiver(storage);
        SimpleArgumentCommandReceiver simpleArgumentCommandReceiver = new SimpleArgumentCommandReceiver(storage);
        this.textReceiver = new TextReceiver();
        this.builderCommandReceiver = new BuilderCommandReceiver(storage);
        ExecuteScriptCommandReceiver executeScriptCommandReceiver = new ExecuteScriptCommandReceiver(builderCommandReceiver, this);
        commandHashMap.put("help", new HelpCommand(displayingCommandReceiver));
        commandHashMap.put("info", new InfoCommand(displayingCommandReceiver));
        commandHashMap.put("show", new ShowCommand(displayingCommandReceiver));
        commandHashMap.put("add", new AddElementCommand(builderCommandReceiver));
        commandHashMap.put("update", new UpdateElementCommand(builderCommandReceiver));
        commandHashMap.put("remove_by_id", new RemoveByIDCommand(simpleArgumentCommandReceiver));
        commandHashMap.put("clear", new ClearCommand(collectionProcessingCommandReceiver));
        commandHashMap.put("save", new SaveCommand(collectionProcessingCommandReceiver));
        commandHashMap.put("execute_script", new ExecuteScriptCommand(executeScriptCommandReceiver));
        commandHashMap.put("add_if_max", new AddIfMaxElementCommand(builderCommandReceiver));
        commandHashMap.put("remove_greater", new RemoveGreaterElementsCommand(builderCommandReceiver));
        commandHashMap.put("reorder", new ReorderCommand(sortingCommandReceiver));
        commandHashMap.put("filter_by_fuel_consumption", new FilterByFuelConsumptionCommand(simpleArgumentCommandReceiver));
        commandHashMap.put("print_ascending", new PrintAscendingCommand(sortingCommandReceiver));
        commandHashMap.put("print_field_ascending_fuel_type", new PrintFieldAscendingFuelTypeCommand(sortingCommandReceiver));
    }

    /**
     * @return map of commands
     */
    public HashMap<String, Command> getCommandHashMap() {
        return commandHashMap;
    }

    /**
     * Gets user input, preprocesses it, gets the command name and calls the command if it matches user input.
     *
     * @param userInput whatever user writes
     */
    public void readUserRequest(String userInput) {
        try {
            // filtering user request, getting command name and arguments, executing the command
            argument = "";
            String[] userInputArray = userInput.split(" ", 2);
            commandName = userInputArray[0];
            if (userInputArray.length == 2) {
                argument = userInputArray[1];
            }
            if (commandHashMap.get(commandName) != null) {
                textReceiver.printReport(commandHashMap.get(commandName).execute(argument));
            } else {
                throw new InvalidCommandNameException();
            }
        } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
            textReceiver.printReport("Empty request. Try again");
        } catch (NoArgumentException e) {
            textReceiver.printReport(commandName + " requires an argument: none were given");
        } catch (NumberFormatException e) {
            textReceiver.printReport(commandName + " requires a different argument type, but " + argument.getClass().getSimpleName() + " was given");
        } catch (InvalidCommandNameException e) {
            textReceiver.printReport("There is no command named \"" + commandName + "\". Try again");
        } catch (IOException e) {
            textReceiver.printReport("Error!");
        } catch (InvalidArgumentsWhileVehicleBuildingViaScriptException e) {
            textReceiver.printReport("An error occurred when building vehicle via script");
            builderCommandReceiver.setScriptMode(false);

        }
    }
}
