package command_factories;

import commands.Command;
import commands.HelpCommand;
import packets.CommandDescriptionPacket;

public class HelpCommandFactory implements CommandFactory {
    @Override
    public Command createCommand(CommandDescriptionPacket packet) {
        return new HelpCommand();
    }
}
