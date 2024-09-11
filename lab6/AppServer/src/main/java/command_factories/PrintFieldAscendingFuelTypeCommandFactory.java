package command_factories;

import commands.Command;
import commands.PrintFieldAscendingFuelTypeCommand;
import packets.CommandDescriptionPacket;

public class PrintFieldAscendingFuelTypeCommandFactory implements CommandFactory {
    @Override
    public Command createCommand(CommandDescriptionPacket packet) {
        return new PrintFieldAscendingFuelTypeCommand();
    }
}
