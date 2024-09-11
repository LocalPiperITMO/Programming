package command_factories;

import commands.Command;
import commands.UpdateElementCommand;
import packets.CommandDescriptionPacket;

public class UpdateCommandFactory implements CommandFactory {
    @Override
    public Command createCommand(CommandDescriptionPacket packet) {
        return new UpdateElementCommand(packet.getCommandElement(),
                Integer.parseInt(packet.getCommandArgument()));
    }
}
