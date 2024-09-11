package command_factories;

import commands.Command;
import commands.PrintAscendingCommand;
import packets.CommandDescriptionPacket;
import packets.PacketWrapper;

public class PrintAscendingCommandFactory implements CommandFactory{
    @Override
    public Command createCommand(CommandDescriptionPacket packet) {
        return new PrintAscendingCommand();
    }
}
