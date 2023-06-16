package command_factories;

import commands.Command;
import packets.CommandDescriptionPacket;
import receivers.TextReceiver;

import java.util.HashMap;

public class FactoryConnector {
    private final TextReceiver receiver = new TextReceiver();
    private final HashMap<String, CommandFactory> factoryHashMap = new HashMap<>();

    public FactoryConnector() {
        factoryHashMap.put("help", new HelpCommandFactory());
        factoryHashMap.put("show", new ShowCommandFactory());
        factoryHashMap.put("info", new InfoCommandFactory());
        factoryHashMap.put("exit", new ExitCommandFactory());
        factoryHashMap.put("add", new AddCommandFactory());
        factoryHashMap.put("add_if_max", new AddIfMaxCommandFactory());
        factoryHashMap.put("remove_greater", new RemoveGreaterCommandFactory());
        factoryHashMap.put("update", new UpdateCommandFactory());
        factoryHashMap.put("clear", new ClearCommandFactory());
        factoryHashMap.put("print_ascending", new PrintAscendingCommandFactory());
        factoryHashMap.put("print_field_ascending_fuel_type", new PrintFieldAscendingFuelTypeCommandFactory());
        factoryHashMap.put("reorder", new ReorderCommandFactory());
        factoryHashMap.put("remove_by_id", new RemoveByIDCommandFactory());
        factoryHashMap.put("filter_by_fuel_consumption", new FilterByFuelConsumptionCommandFactory());
    }

    public Command convertPacketToCommand(CommandDescriptionPacket packet) {
        receiver.printToLog(this.getClass().getSimpleName(), "Received command - " + packet.getCommandName());
        return factoryHashMap.get(packet.getCommandName()).createCommand(packet);
    }
}