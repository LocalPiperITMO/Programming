package command_factories;

import commands.Command;
import commands.RemoveGreaterElementsCommand;
import packets.CommandDescriptionPacket;

public class RemoveGreaterCommandFactory implements CommandFactory {
    @Override
    public Command createCommand(CommandDescriptionPacket packet) {
        return new RemoveGreaterElementsCommand(packet.getCommandElement());
    }
}
