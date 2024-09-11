package wrapping;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class PacketWrapper {

    public <T extends Serializable> CollectionPacket<T> wrapCollection(CollectionPacket.PacketIdentifier packetIdentifier, LinkedHashMap<Integer, ? extends T> dataset) {
        return new CollectionPacket<>(packetIdentifier, dataset);
    }

    public ReportPacket wrapReport(String report) {
        return new ReportPacket(report);
    }

    public CommandDescriptionPacket wrapCDP(String username, String password, String commandName) {
        return new CommandDescriptionPacket(username, password, commandName);
    }

    public CommandDescriptionPacket wrapCDP(String username, String password, String commandName, String commandArgument) {
        return new CommandDescriptionPacket(username, password, commandName, commandArgument);
    }

    public CommandDescriptionPacket wrapCDP(String username, String password, String commandName, Serializable vehicle) {
        return new CommandDescriptionPacket(username, password, commandName, vehicle);
    }

    public CommandDescriptionPacket wrapCDP(String username, String password, String commandName, String commandArgument, Serializable vehicle) {
        return new CommandDescriptionPacket(username, password, commandName, commandArgument, vehicle);
    }

    public CommandInstructionsPacket wrapCIP(HashMap<String, String[]> commandMap) {
        return new CommandInstructionsPacket(commandMap);
    }

    public AuthorizationPacket wrapAP(String username, String password, boolean isReg) {
        return new AuthorizationPacket(username, password, isReg);
    }

}