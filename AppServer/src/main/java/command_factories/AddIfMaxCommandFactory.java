package command_factories;

import commands.AddIfMaxElementCommand;
import commands.Command;
import packets.CommandDescriptionPacket;
import packets.PacketWrapper;

public class AddIfMaxCommandFactory implements CommandFactory{
    @Override
    public Command createCommand(CommandDescriptionPacket packet) {
        return new AddIfMaxElementCommand(packet.getCommandElement());
    }
}
