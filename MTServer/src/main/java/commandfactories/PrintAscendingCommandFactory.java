package commandfactories;

import commands.Command;
import commands.PrintAscendingCommand;
import wrapping.CommandDescriptionPacket;

public class PrintAscendingCommandFactory implements CommandFactory{
    @Override
    public Command createCommand(CommandDescriptionPacket packet) {
        return new PrintAscendingCommand();
    }
}
