package commands;

import receivers.SortingCommandReceiver;

import java.io.Serializable;

/**
 * Command for printing elements sorted by their fuelType
 */
public class PrintFieldAscendingFuelTypeCommand implements Command {



    public Serializable execute() {
        SortingCommandReceiver receiver = new SortingCommandReceiver();
        return receiver.printFieldAscendingFuelType();
    }
}
