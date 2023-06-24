package commandfactories;

import commands.Command;
import commands.HelpCommand;
import wrapping.CommandDescriptionPacket;

public class HelpCommandFactory implements CommandFactory {
    @Override
    public Command createCommand(CommandDescriptionPacket packet) {
        return new HelpCommand();
    }
}
