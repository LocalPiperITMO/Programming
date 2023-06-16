package commands;

import receivers.CollectionModifyingCommandReceiver;

import java.io.Serializable;

/**
 * Command for clearing the collection.
 */
public class ClearCommand implements Command {



    public Serializable execute() {
        CollectionModifyingCommandReceiver receiver = new CollectionModifyingCommandReceiver();
        return receiver.clear();
    }
}
