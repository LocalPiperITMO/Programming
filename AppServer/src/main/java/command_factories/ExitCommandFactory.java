package command_factories;

import commands.Command;
import commands.ExitCommand;
import packets.CommandDescriptionPacket;

public class ExitCommandFactory implements CommandFactory {
    @Override
    public Command createCommand(CommandDescriptionPacket packet) {
        return new ExitCommand();
    }
}
