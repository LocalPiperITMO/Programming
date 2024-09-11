package packets;

import data.Vehicle;

import java.io.Serializable;

public class CommandDescriptionPacket implements Serializable {
    private final String commandName;
    private final String commandArgument;
    private final Vehicle commandElement;

    public CommandDescriptionPacket(String commandName) {
        this(commandName, null, null);
    }

    public CommandDescriptionPacket(String commandName, String commandArgument) {
        this(commandName, commandArgument, null);
    }

    public CommandDescriptionPacket(String commandName, Serializable commandElement) {
        this(commandName, null, commandElement);
    }

    public CommandDescriptionPacket(String commandName, String commandArgument, Serializable commandElement) {
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

}
