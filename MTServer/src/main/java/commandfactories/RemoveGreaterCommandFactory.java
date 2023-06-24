package commandfactories;

import commands.Command;
import commands.RemoveGreaterElementsCommand;
import wrapping.CommandDescriptionPacket;

public class RemoveGreaterCommandFactory implements CommandFactory {
    @Override
    public Command createCommand(CommandDescriptionPacket packet) {
        return new RemoveGreaterElementsCommand(packet.getCommandElement(), packet.getUsername());
    }
}
