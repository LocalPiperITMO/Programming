package commands;

import java.util.HashMap;
import java.util.Map;

public class HelpCommand implements Command {
    private final HashMap<String, Command> map;

    /**
     * "help" command
     * Displays description of every command
     *
     * @param map stores commands
     */
    public HelpCommand(HashMap<String, Command> map) {
        this.map = map;
    }

    /**
     * Used for showing information.
     *
     * @return information about the command
     */
    public String showInfo() {
        return "No argument required. Displays this message";
    }

    /**
     * Executes command
     * @param arg              command argument
     * @param isCalledByScript checks if command called from script
     */
    public void execute(String arg, boolean isCalledByScript) {
        System.out.println("""
                The conventions are as follows:
                No argument required : after inputting command name one DOES NOT need to input an argument.
                Required argument - argumentName(argumentType) : after inputting command name press SPACE BAR and then print the value of an argument of the required type
                User builds an element : after inputting command name and pressing ENTER the user is welcomed by VehicleBuilder
                                
                The command names and their descriptions are as follows:""");
        for (Map.Entry<String, Command> set : map.entrySet()) {
            System.out.println(set.getKey() + " : " + set.getValue().showInfo());
        }
    }
}
