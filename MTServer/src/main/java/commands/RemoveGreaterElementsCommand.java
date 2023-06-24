package commands;

import datatypes.Vehicle;
import receivers.DatabaseModifyingCommandReceiver;

import java.io.Serializable;

/**
 * Command for removing elements from the collection if the condition (greater than the current) is matched.
 */
public class RemoveGreaterElementsCommand implements Command {

    private final Vehicle vehicle;
    private final String name;

    public RemoveGreaterElementsCommand(Vehicle vehicle, String username) {
        this.vehicle = vehicle;
        this.name = username;
    }

    public Serializable execute() {
        DatabaseModifyingCommandReceiver receiver = new DatabaseModifyingCommandReceiver();
        receiver.setUsername(name);
        return receiver.removeGreater(vehicle);
    }
}