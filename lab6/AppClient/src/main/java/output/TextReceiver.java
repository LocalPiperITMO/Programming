package output;

import data.Vehicle;
import packets.CollectionPacket;

import java.io.Serializable;
import java.util.Collection;

public class TextReceiver {
    public void print(String s) {
        System.out.println(s);
        System.out.println();
    }

    public void printCollection(CollectionPacket.PacketIdentifier packetIdentifier, Collection<? extends Serializable> collection) {
        if (packetIdentifier.equals(CollectionPacket.PacketIdentifier.COMMAND_INFO)) {
            System.out.println("""
                    Legend:
                    No argument required: Self-explanatory. This command does not need an argument. If user passes an argument, will reply with "Redundant argument" message
                    Required argument - argName(argType): Command requires a argName argument of argType. If user does not pass an argument/passes an argument of wrong type, replies with corresponding message.
                    Some commands may ask for arguments that match specified bounds.
                    Building command: If called outside the script, will ask user to build an element step-by-step (process can be interrupted). If not, will build an element using a number of script lines as arguments.
                                        
                    The commands are as follows:
                    """);
            for (Serializable element : collection) {
                System.out.println(element.toString());
            }

        } else if (packetIdentifier.equals(CollectionPacket.PacketIdentifier.FUEL_TYPE_COLLECTION)) {
            System.out.printf("%7s " + "%-10s\n", "ID", "FuelType");
            for (Serializable element : collection) {
                if (element instanceof Vehicle vehicle) {
                    System.out.printf("%7d " + "%-10s\n", vehicle.getId(), vehicle.getFuelType());
                }
            }
        } else {
            System.out.printf("%7s " + "%-20s " + "%12s " + "%15s " + "%11s " + "%15s " + "%15s " + "%-10s " + "%-10s\n",
                    "ID",
                    "Name",
                    "CreationDate",
                    "X",
                    "Y",
                    "EnginePower",
                    "FuelConsumption",
                    "Type",
                    "FuelType");
            for (Serializable element : collection) {
                if (element instanceof Vehicle vehicle) {
                    System.out.printf("%7s " + "%-20s " + "%12s " + "%15s " + "%11s " + "%15s " + "%15s " + "%-10s " + "%-10s\n",
                            vehicle.getId(),
                            vehicle.getName(),
                            vehicle.getCreationDate(),
                            vehicle.getCoordinates().getX(),
                            vehicle.getCoordinates().getY(),
                            vehicle.getEnginePower(),
                            vehicle.getFuelConsumption(),
                            vehicle.getType(),
                            vehicle.getFuelType());
                }
            }
        }
        System.out.println();
    }
}
