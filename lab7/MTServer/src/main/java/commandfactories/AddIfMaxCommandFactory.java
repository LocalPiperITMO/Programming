package commandfactories;

import commands.AddIfMaxElementCommand;
import commands.Command;
import wrapping.CommandDescriptionPacket;

public class AddIfMaxCommandFactory implements CommandFactory {
    @Override
    public Command createCommand(CommandDescriptionPacket packet) {
        return new AddIfMaxElementCommand(packet.getCommandElement(),packet.getUsername());
    }
}
