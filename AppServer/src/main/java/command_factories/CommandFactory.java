package command_factories;

import commands.Command;
import packets.CommandDescriptionPacket;

@FunctionalInterface
public interface CommandFactory {
    Command createCommand(CommandDescriptionPacket packet);
}
