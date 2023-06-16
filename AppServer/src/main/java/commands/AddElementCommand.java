package commands;

import data.Vehicle;
import receivers.CollectionModifyingCommandReceiver;

import java.io.Serializable;

/**
 * Command for adding elements to the collection.
 */
public class AddElementCommand implements Command {
    private final Vehicle vehicle;

    public AddElementCommand(Vehicle vehicle) {
        this.vehicle = vehicle;
    }




    public Serializable execute() {
        CollectionModifyingCommandReceiver receiver = new CollectionModifyingCommandReceiver();
        return receiver.addVehicle(vehicle);
    }
}
