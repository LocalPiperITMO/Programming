package commands;

import datatypes.Vehicle;
import receivers.DatabaseModifyingCommandReceiver;

import java.io.Serializable;

/**
 * Command for adding elements to the collection.
 */
public class AddElementCommand implements Command {
    private final Vehicle vehicle;
    private final String name;

    public AddElementCommand(Vehicle vehicle, String username) {
        this.vehicle = vehicle;
        this.name = username;
    }


    public Serializable execute() {
        DatabaseModifyingCommandReceiver receiver = new DatabaseModifyingCommandReceiver();
        receiver.setUsername(name);
        return receiver.addVehicle(vehicle);
    }
}
