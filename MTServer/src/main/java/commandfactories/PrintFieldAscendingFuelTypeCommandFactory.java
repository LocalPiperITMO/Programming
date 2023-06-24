package commandfactories;

import commands.Command;
import commands.PrintFieldAscendingFuelTypeCommand;
import wrapping.CommandDescriptionPacket;

public class PrintFieldAscendingFuelTypeCommandFactory implements CommandFactory {
    @Override
    public Command createCommand(CommandDescriptionPacket packet) {
        return new PrintFieldAscendingFuelTypeCommand();
    }
}
