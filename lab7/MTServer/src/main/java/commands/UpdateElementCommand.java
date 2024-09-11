package commands;

import datatypes.Vehicle;
import receivers.DatabaseModifyingCommandReceiver;

import java.io.Serializable;

/**
 * Command for rewriting existing elements.
 */
public class UpdateElementCommand implements Command {

    private final int id;
    private final Vehicle vehicle;
    private final String name;

    public UpdateElementCommand(Vehicle vehicle, int id, String username) {
        this.vehicle = vehicle;
        this.id = id;
        this.name = username;
    }

    public Serializable execute() {
        DatabaseModifyingCommandReceiver receiver = new DatabaseModifyingCommandReceiver();
        receiver.setUsername(name);
        return receiver.update(vehicle, id);
    }
}