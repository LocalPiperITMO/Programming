package commands;

import java.util.HashMap;

public class CommandLibrary {
    private final HashMap<String, String[]> commandLibrary;

    public CommandLibrary() {
        commandLibrary = new HashMap<>();
        commandLibrary.put("help", new String[]{"", ""});
        commandLibrary.put("show", new String[]{"", ""});
        commandLibrary.put("info", new String[]{"", ""});
        commandLibrary.put("exit", new String[]{"", ""});
        commandLibrary.put("reorder", new String[]{"", ""});
        commandLibrary.put("print_ascending", new String[]{"", ""});
        commandLibrary.put("print_field_ascending_fuel_type", new String[]{"", ""});
        commandLibrary.put("clear", new String[]{"", ""});
        commandLibrary.put("remove_by_id", new String[]{"", "Integer"});
        commandLibrary.put("filter_by_fuel_consumption", new String[]{"", "Long"});
        commandLibrary.put("add", new String[]{"Vehicle", ""});
        commandLibrary.put("add_if_max", new String[]{"Vehicle", ""});
        commandLibrary.put("remove_greater", new String[]{"Vehicle", ""});
        commandLibrary.put("update", new String[]{"Vehicle", "Integer"});
    }

    public HashMap<String, String[]> getCommandLibrary() {
        return commandLibrary;
    }
}
