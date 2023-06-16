package commands;

import data.Vehicle;
import receivers.CollectionModifyingCommandReceiver;

import java.io.Serializable;

/**
 * Command for adding elements to the collection if the condition (greater than the greatest) is matched.
 */
public class AddIfMaxElementCommand implements Command {

    private final Vehicle vehicle;

    /**
     * @return information about the command
     */
    public AddIfMaxElementCommand(Vehicle vehicle) {
        this.vehicle = vehicle;
    }



    public Serializable execute() {
        CollectionModifyingCommandReceiver receiver = new CollectionModifyingCommandReceiver();
        return receiver.addIfMax(vehicle);
    }
}