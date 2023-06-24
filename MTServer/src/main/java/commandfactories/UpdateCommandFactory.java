package commandfactories;

import commands.Command;
import commands.UpdateElementCommand;
import wrapping.CommandDescriptionPacket;

public class UpdateCommandFactory implements CommandFactory {
    @Override
    public Command createCommand(CommandDescriptionPacket packet) {
        return new UpdateElementCommand(packet.getCommandElement(),
                Integer.parseInt(packet.getCommandArgument()),
                packet.getUsername());
    }
}
