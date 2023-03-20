package commands;

import exceptions.NoArgumentException;
import receivers.SimpleArgumentCommandReceiver;

public class FilterByFuelConsumptionCommand implements Command {
    private final SimpleArgumentCommandReceiver receiver;

    /**
     * "filter_by_fuel_consumption fuel_consumption" command.
     * Deletes all elements with the fuel_consumption value lower than one given as argument from the collection
     *
     * @param receiver used for storing the collection
     */
    public FilterByFuelConsumptionCommand(SimpleArgumentCommandReceiver receiver) {
        this.receiver = receiver;
    }

    /**
     * Used for showing information.
     *
     * @return information about the command
     */
    public String showInfo() {
        return "Required argument - fuelConsumption(number). Displays elements with the given fuelConsumption";
    }

    /**
     * Executes command
     *
     * @param argument command argument
     * @throws NoArgumentException if command requires argument but none were given
     */
    public void execute(String argument) throws NoArgumentException {
        if (argument.length() != 0) {
            long fuelConsumption = Long.parseLong(argument);
            receiver.filterByFuelConsumption(fuelConsumption);
        } else {
            throw new NoArgumentException();
        }
    }
}
