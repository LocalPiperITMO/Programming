package commands;

import receivers.SortingCommandReceiver;

import java.io.Serializable;

/**
 * Command for printing elements in ascending order (elements sorted by sum)
 */
public class PrintAscendingCommand implements Command {


    public Serializable execute() {
        SortingCommandReceiver receiver = new SortingCommandReceiver();
        return receiver.printAscending();
    }
}
