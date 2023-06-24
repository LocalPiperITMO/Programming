package commands;

import receivers.DatabaseModifyingCommandReceiver;

import java.io.Serializable;

/**
 * Command for clearing the collection.
 */
public class ClearCommand implements Command {
    private final String username;

    public ClearCommand(String username) {
        this.username = username;
    }

    public Serializable execute() {
        DatabaseModifyingCommandReceiver receiver = new DatabaseModifyingCommandReceiver();
        receiver.setUsername(username);
        return receiver.clear();
    }
}
