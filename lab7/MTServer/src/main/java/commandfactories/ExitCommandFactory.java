package commandfactories;

import commands.Command;
import commands.ExitCommand;
import wrapping.CommandDescriptionPacket;

public class ExitCommandFactory implements CommandFactory {
    @Override
    public Command createCommand(CommandDescriptionPacket packet) {
        return new ExitCommand();
    }
}
