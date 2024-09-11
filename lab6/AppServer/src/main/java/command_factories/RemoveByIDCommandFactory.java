package command_factories;

import commands.Command;
import commands.RemoveByIDCommand;
import packets.CommandDescriptionPacket;

public class RemoveByIDCommandFactory implements CommandFactory {
    public Command createCommand(CommandDescriptionPacket packet) {
        return new RemoveByIDCommand(Integer.parseInt(packet.getCommandArgument()));
    }
}
