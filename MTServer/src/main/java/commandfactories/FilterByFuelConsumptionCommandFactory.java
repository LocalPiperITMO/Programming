package commandfactories;

import commands.Command;
import commands.FilterByFuelConsumptionCommand;
import wrapping.CommandDescriptionPacket;

public class FilterByFuelConsumptionCommandFactory implements CommandFactory {
    @Override
    public Command createCommand(CommandDescriptionPacket packet) {
        return new FilterByFuelConsumptionCommand(Long.parseLong(packet.getCommandArgument()));
    }
}
