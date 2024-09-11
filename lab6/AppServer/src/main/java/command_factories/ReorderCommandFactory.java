package command_factories;

import commands.Command;
import commands.ReorderCommand;
import packets.CommandDescriptionPacket;

public class ReorderCommandFactory implements CommandFactory {
    @Override
    public Command createCommand(CommandDescriptionPacket packet) {
        return new ReorderCommand();
    }
}
