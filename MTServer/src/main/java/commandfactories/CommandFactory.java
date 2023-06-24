package commandfactories;

import commands.Command;
import wrapping.CommandDescriptionPacket;

@FunctionalInterface
public interface CommandFactory{
    Command createCommand(CommandDescriptionPacket packet);
}
