package packets;

import data.Vehicle;

import java.io.Serializable;
import java.util.Collection;

public class PacketWrapper {

    public <T extends Serializable> CollectionPacket<T> wrapCollection(CollectionPacket.PacketIdentifier packetIdentifier, Collection<? extends T> dataset) {
        return new CollectionPacket<>(packetIdentifier, dataset);
    }

    public ReportPacket wrapReport(String report) {
        return new ReportPacket(report);
    }

    public CommandDescriptionPacket wrapCDP(String commandName) {
        return new CommandDescriptionPacket(commandName);
    }

    public CommandDescriptionPacket wrapCDP(String commandName, String commandArgument) {
        return new CommandDescriptionPacket(commandName, commandArgument);
    }

    public CommandDescriptionPacket wrapCDP(String commandName, Serializable vehicle) {
        return new CommandDescriptionPacket(commandName, vehicle);
    }

    public CommandDescriptionPacket wrapCDP(String commandName, String commandArgument, Serializable vehicle) {
        return new CommandDescriptionPacket(commandName, commandArgument, vehicle);
    }

}