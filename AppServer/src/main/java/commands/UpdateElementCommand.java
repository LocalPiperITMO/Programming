package commands;

import data.Vehicle;
import receivers.CollectionModifyingCommandReceiver;

import java.io.Serializable;

/**
 * Command for rewriting existing elements.
 */
public class UpdateElementCommand implements Command {

    private final int id;
    private final Vehicle vehicle;

    public UpdateElementCommand(Vehicle vehicle, int id) {
        this.vehicle = vehicle;
        this.id = id;
    }





    public Serializable execute() {
        CollectionModifyingCommandReceiver receiver = new CollectionModifyingCommandReceiver();
        return receiver.update(vehicle, id);
    }
}