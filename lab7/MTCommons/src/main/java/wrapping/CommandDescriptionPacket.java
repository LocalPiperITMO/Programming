package wrapping;

import datatypes.Vehicle;

import java.io.Serializable;

public class CommandDescriptionPacket implements Serializable {
    private final String username;
    private final String password;
    private final String commandName;
    private final String commandArgument;
    private final Vehicle commandElement;

    public CommandDescriptionPacket(String username, String password, String commandName) {
        this(username, password, commandName, null, null);
    }

    public CommandDescriptionPacket(String username,String password, String commandName, String commandArgument) {
        this(username, password, commandName, commandArgument, null);
    }

    public CommandDescriptionPacket(String username, String password, String commandName, Serializable commandElement) {
        this(username, password, commandName, null, commandElement);
    }

    public CommandDescriptionPacket(String username, String password, String commandName, String commandArgument, Serializable commandElement) {
        this.username = username;
        this.password = password;
        this.commandName = commandName;
        this.commandArgument = commandArgument;
        this.commandElement = (Vehicle) commandElement;
    }

    public String getCommandName() {
        return commandName;
    }

    public String getCommandArgument() {
        return commandArgument;
    }

    public Vehicle getCommandElement() {
        return commandElement;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() { return password;}
}
