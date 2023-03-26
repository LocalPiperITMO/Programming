package commands;

import receivers.SortingCommandReceiver;

public class PrintFieldAscendingFuelTypeCommand implements Command {
    private final SortingCommandReceiver receiver;

    /**
     * "print_field_ascending_fuel_type" command
     * Sorts the collection in ascending order. Elements are compared using fuelType
     * Prints the collection after sorting
     *
     * @param receiver used for storing the collection
     */
    public PrintFieldAscendingFuelTypeCommand(SortingCommandReceiver receiver) {
        this.receiver = receiver;
    }

    /**
     * Used for showing information.
     *
     * @return information about the command
     */
    public String showInfo() {
        return "No argument required. Displays only ID and FuelType of every element, sorted by FuelType";
    }

    /**
     * Executes command
     *
     * @param arg command argument
     */
    public String execute(String arg) {
        return receiver.printFieldAscendingFuelType();
    }
}
