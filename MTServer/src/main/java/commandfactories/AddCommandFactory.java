package commandfactories;

import commands.AddElementCommand;
import commands.Command;
import wrapping.CommandDescriptionPacket;

public class AddCommandFactory implements CommandFactory {
    @Override
    public Command createCommand(CommandDescriptionPacket packet) {
        return new AddElementCommand(packet.getCommandElement(),packet.getUsername());
    }
}
