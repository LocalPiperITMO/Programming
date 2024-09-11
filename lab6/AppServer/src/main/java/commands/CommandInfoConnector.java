package commands;

import java.util.HashMap;
import java.util.Map;

public class CommandInfoConnector {
    private final Map<String, String> commandInfoMap = new HashMap<>();

    public CommandInfoConnector() {
        commandInfoMap.put("help", "No argument required. Displays this message");
        commandInfoMap.put("show", "No argument required. Displays every element of the dataset");
        commandInfoMap.put("info", "No argument required. Displays the information about the dataset (type of dataset, creation date, number of elements)");
        commandInfoMap.put("clear", "No argument required. Clears the dataset");
        commandInfoMap.put("reorder", "No argument required. Displays every element of the dataset in reverse order of the current sorting");
        commandInfoMap.put("add", "No argument required. Building command. Adds pre-built element to dataset");
        commandInfoMap.put("update", "Required argument - ID(integer). Building command. Updates the element with the given ID, if one exists");
        commandInfoMap.put("add_if_max", "No argument required. Building command. If the element is greater that the greatest element in the dataset, it is added to dataset");
        commandInfoMap.put("remove_by_id", "Required argument - ID(integer). Removes an element with the given ID, if one exists");
        commandInfoMap.put("filter_by_fuel_consumption", "Required argument - fuelConsumption(long). Filters the collection, leaving elements that match given value");
        commandInfoMap.put("remove_greater", "No argument required. Building command. Removes all the elements from the dataset greater than the given one");
        commandInfoMap.put("print_ascending", "No argument required. Displays every element sorted by EnginePower, then by FuelConsumption, then by ID");
        commandInfoMap.put("print_field_ascending_fuel_type", "No argument required. Displays only ID and FuelType of every element, sorted by FuelType, then by ID");
        commandInfoMap.put("exit", "No argument required. Ends the session. Collection is saved");
        commandInfoMap.put("/stop", "No argument required. Interrupts building process. Works only during element building");
        commandInfoMap.put("execute_script", "Required argument - path(string). Opens file and executes commands from it");
    }

    public Map<String, String> getCommandInfoMap() {
        return commandInfoMap;
    }
}
