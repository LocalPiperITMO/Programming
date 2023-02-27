package pattern;


import commands.HelpCommand;
import commands.InfoCommand;
import commands.ShowCommand;
import validators.UserRequestValidator;

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
    }

    public void getRequestFromUser(String userInput) {
        try {
            String[] userInputArray = userInput.split(" ", 2);
            String commandName = userInputArray[0];
            commandHashMap.get(commandName).execute();
            System.out.println();
        } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
            validator.emptyRequest();
        } catch (NullPointerException npe) {
            validator.invalidCommandRequest(userInput.split(" ", 2)[0]);
        }
    }
}
