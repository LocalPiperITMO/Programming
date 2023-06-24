package commandfactories;

import commands.Command;
import commands.ReorderCommand;
import wrapping.CommandDescriptionPacket;

public class ReorderCommandFactory implements CommandFactory {
    @Override
    public Command createCommand(CommandDescriptionPacket packet) {
        return new ReorderCommand();
    }
}
