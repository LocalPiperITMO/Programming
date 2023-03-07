package commands;

import java.util.HashMap;
import java.util.Map;

public class HelpCommand implements Command {
    private final HashMap<String, Command> map;

    public HelpCommand(HashMap<String, Command> map) {
        this.map = map;
    }

    public String showInfo() {
        return "No argument required. Displays this message";
    }

    public void execute(String arg) {
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
