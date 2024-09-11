package commands;

import receivers.DatabaseModifyingCommandReceiver;

import java.io.Serializable;

/**
 * Command for removing existing elements
 */
public class RemoveByIDCommand implements Command {
    private final int id;
    private final String name;

    public RemoveByIDCommand(int id, String username) {
        this.id = id;
        this.name = username;
    }

    public Serializable execute() {
        DatabaseModifyingCommandReceiver receiver = new DatabaseModifyingCommandReceiver();
        receiver.setUsername(name);
        return receiver.removeByID(id);
    }
}
