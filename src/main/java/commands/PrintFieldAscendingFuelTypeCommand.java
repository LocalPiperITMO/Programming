package commands;

import receivers.SortingCommandReceiver;

/**
 * Command for printing elements sorted by their fuelType
 */
public class PrintFieldAscendingFuelTypeCommand implements Command {

    private final SortingCommandReceiver receiver;

    /**
     * @param receiver receiver with command realization
     */
    public PrintFieldAscendingFuelTypeCommand(SortingCommandReceiver receiver) {
        this.receiver = receiver;
    }

    /**
     * @return information about the command
     */
    public String showInfo() {
        return "No argument required. Displays only ID and FuelType of every element, sorted by FuelType, then by ID";
    }

    /**
     * Calls method from the receiver
     *
     * @param arg command argument
     * @return report on command execution
     */
    public String execute(String arg) {
        return receiver.printFieldAscendingFuelType();
    }
}
