package commandfactories;

import commands.Command;
import commands.ShowCommand;
import wrapping.CommandDescriptionPacket;

public class ShowCommandFactory implements CommandFactory {
    @Override
    public Command createCommand(CommandDescriptionPacket packet) {
        return new ShowCommand();
    }
}
