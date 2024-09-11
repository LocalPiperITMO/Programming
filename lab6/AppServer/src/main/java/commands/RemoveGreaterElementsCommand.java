package commands;

import data.Vehicle;
import receivers.CollectionModifyingCommandReceiver;

import java.io.Serializable;

/**
 * Command for removing elements from the collection if the condition (greater than the current) is matched.
 */
public class RemoveGreaterElementsCommand implements Command {

    private final Vehicle vehicle;

    public RemoveGreaterElementsCommand(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Serializable execute() {
        CollectionModifyingCommandReceiver receiver = new CollectionModifyingCommandReceiver();
        return receiver.removeGreater(vehicle);
    }
}