package commandfactories;

import commands.Command;
import commands.InfoCommand;
import wrapping.CommandDescriptionPacket;

public class InfoCommandFactory implements CommandFactory {
    @Override
    public Command createCommand(CommandDescriptionPacket packet) {
        return new InfoCommand();
    }
}
