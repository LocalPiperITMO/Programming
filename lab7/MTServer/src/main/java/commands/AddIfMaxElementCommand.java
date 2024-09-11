package commands;

import datatypes.Vehicle;
import receivers.DatabaseModifyingCommandReceiver;

import java.io.Serializable;

/**
 * Command for adding elements to the collection if the condition (greater than the greatest) is matched.
 */
public class AddIfMaxElementCommand implements Command {

    private final Vehicle vehicle;
    private final String name;

    public AddIfMaxElementCommand(Vehicle vehicle, String username) {
        this.vehicle = vehicle;
        this.name = username;
    }



    public Serializable execute() {
        DatabaseModifyingCommandReceiver receiver = new DatabaseModifyingCommandReceiver();
        receiver.setUsername(name);
        return receiver.addIfMax(vehicle);
    }
}