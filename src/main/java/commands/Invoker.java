package commands;


import exceptions.InvalidArgumentsWhileVehicleBuildingViaScriptException;
import exceptions.InvalidCommandNameException;
import exceptions.NoArgumentException;

import java.io.IOException;
import java.util.*;

public class Invoker {
    private final HashMap<String, Command> commandHashMap;
    private String argument;
    private String commandName;
    private boolean isCalledByScript;
    private List<String> listOfArgumentsForBuildingViaScript;
    private Set<String> setOfScriptPaths;

    public Invoker(Receiver receiver) {
        this.commandHashMap = new HashMap<>();
        this.listOfArgumentsForBuildingViaScript = new ArrayList<>();
        this.setOfScriptPaths = new HashSet<>();
        commandHashMap.put("help", new HelpCommand(commandHashMap));
        commandHashMap.put("info", new InfoCommand(receiver));
        commandHashMap.put("show", new ShowCommand(receiver));
        commandHashMap.put("add", new AddElementCommand(receiver, this));
        commandHashMap.put("update", new UpdateElementCommand(receiver, this));
        commandHashMap.put("remove_by_id", new RemoveByIDCommand(receiver));
        commandHashMap.put("clear", new ClearCommand(receiver));
        commandHashMap.put("save", new SaveCommand(receiver));
        commandHashMap.put("execute_script", new ExecuteScriptCommand(this));
        commandHashMap.put("add_if_max", new AddIfMaxElementCommand(receiver, this));
        commandHashMap.put("remove_greater", new RemoveGreaterElementsCommand(receiver, this));
        commandHashMap.put("reorder", new ReorderCommand(receiver));
        commandHashMap.put("filter_by_fuel_consumption", new FilterByFuelConsumptionCommand(receiver));
        commandHashMap.put("print_ascending", new PrintAscendingCommand(receiver));
        commandHashMap.put("print_field_ascending_fuel_type", new PrintFieldAscendingFuelTypeCommand(receiver));
    }

    public Set<String> getSetOfScriptPaths() {
        return this.setOfScriptPaths;
    }

    public boolean isCalledByScript() {
        return isCalledByScript;
    }

    public void setCalledByScript(boolean isCalledByScript) {
        this.isCalledByScript = isCalledByScript;
    }

    public void setListOfArgumentsForBuildingViaScript(List<String> listOfArguments) {
        this.listOfArgumentsForBuildingViaScript = listOfArguments;
    }

    public List<String> getListOfArgumentsForBuildingViaScript() {
        return listOfArgumentsForBuildingViaScript;
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
                commandHashMap.get(commandName).execute(argument, this.isCalledByScript);
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
        } catch (InvalidArgumentsWhileVehicleBuildingViaScriptException e) {
            System.out.println("An error occurred when building vehicle via script");
            setCalledByScript(false);
        }
    }
}
