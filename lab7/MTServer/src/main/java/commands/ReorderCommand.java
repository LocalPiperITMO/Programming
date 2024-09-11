package commands;

import receivers.SortingCommandReceiver;

import java.io.Serializable;

/**
 * Command for printing elements in reversed order
 */
public class ReorderCommand implements Command {





    public Serializable execute() {
        SortingCommandReceiver receiver = new SortingCommandReceiver();
        return receiver.reorder();
    }
}
