package commands;

import exceptions.NoArgumentException;
import receivers.SimpleArgumentCommandReceiver;

/**
 * Command for printing Vehicles that match the condition (if Vehicle's fuelConsumption is equal to the given)
 */
public class FilterByFuelConsumptionCommand implements Command {
    /**
     * Receiver that contains required method for the command
     */
    private final SimpleArgumentCommandReceiver receiver;

    /**
     * @param receiver receiver with command realization
     */
    public FilterByFuelConsumptionCommand(SimpleArgumentCommandReceiver receiver) {
        this.receiver = receiver;
    }

    /**
     * @return information about the command
     */
    public String showInfo() {
        return "Required argument - fuelConsumption(number). Displays elements with the given fuelConsumption";
    }

    /**
     * Calls method from the receiver
     *
     * @param arg fuelConsumption
     * @return report on command execution
     * @throws NoArgumentException if no argument was given
     */
    public String execute(String arg) throws NoArgumentException {
        if (arg.length() != 0) {
            long fuelConsumption = Long.parseLong(arg);
            return receiver.filterByFuelConsumption(fuelConsumption);
        } else {
            throw new NoArgumentException();
        }
    }
}
