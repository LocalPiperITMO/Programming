package command_factories;

import commands.Command;
import commands.InfoCommand;
import packets.CommandDescriptionPacket;

public class InfoCommandFactory implements CommandFactory {
    @Override
    public Command createCommand(CommandDescriptionPacket packet) {
        return new InfoCommand();
    }
}
