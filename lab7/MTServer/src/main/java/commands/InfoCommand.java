package commands;

import receivers.DisplayingCommandReceiver;

import java.io.Serializable;

/**
 * Command for printing information about the collection.
 * Shows number of elements, creation date and collection type
 */
public class InfoCommand implements Command {




    public Serializable execute() {
        DisplayingCommandReceiver receiver = new DisplayingCommandReceiver();
        return receiver.info();
    }
}
