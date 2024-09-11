package command_factories;

import commands.ClearCommand;
import commands.Command;
import packets.CommandDescriptionPacket;

public class ClearCommandFactory implements CommandFactory {
    @Override
    public Command createCommand(CommandDescriptionPacket packet) {
        return new ClearCommand();
    }
}
