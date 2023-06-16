package command_factories;

import commands.AddElementCommand;
import commands.Command;
import packets.CommandDescriptionPacket;

public class AddCommandFactory implements CommandFactory {
    @Override
    public Command createCommand(CommandDescriptionPacket packet) {
        return new AddElementCommand(packet.getCommandElement());
    }
}
