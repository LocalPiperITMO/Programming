package commandfactories;

import commands.ClearCommand;
import commands.Command;
import wrapping.CommandDescriptionPacket;

public class ClearCommandFactory implements CommandFactory {
    @Override
    public Command createCommand(CommandDescriptionPacket packet) {
        return new ClearCommand(packet.getUsername());
    }
}
