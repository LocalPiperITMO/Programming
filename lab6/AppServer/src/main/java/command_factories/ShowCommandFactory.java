package command_factories;

import commands.Command;
import commands.ShowCommand;
import packets.CommandDescriptionPacket;

public class ShowCommandFactory implements CommandFactory {
    @Override
    public Command createCommand(CommandDescriptionPacket packet) {
        return new ShowCommand();
    }
}
