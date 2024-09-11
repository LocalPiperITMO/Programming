package commandfactories;

import commands.Command;
import commands.RemoveByIDCommand;
import wrapping.CommandDescriptionPacket;

public class RemoveByIDCommandFactory implements CommandFactory {
    public Command createCommand(CommandDescriptionPacket packet) {
        return new RemoveByIDCommand(Integer.parseInt(packet.getCommandArgument()),packet.getUsername());
    }
}
