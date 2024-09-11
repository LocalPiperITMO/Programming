package commands;

import exceptions.NoArgumentException;
import org.apache.commons.lang3.math.NumberUtils;
import receivers.SimpleArgumentCommandReceiver;

/**
 * Command for printing Vehicles that match the condition (if Vehicle's fuelConsumption is equal to the given)
 */
public class FilterByFuelConsumptionCommand implements Command {

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
        return "Required argument - fuelConsumption(number). Displays elements with the given fuelConsumption, if they exist";
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
            if (NumberUtils.isParsable(arg)) {
                try {
                    long fuelConsumption = Long.parseLong(arg);
                    if (fuelConsumption > 0 && fuelConsumption <= 4000000000L) {
                        return receiver.filterByFuelConsumption(fuelConsumption);
                    }
                    return "Argument does not belong to bounds (0,4000000000]. Command execution failed";
                } catch (NumberFormatException numberFormatException) {
                    return "Argument does not belong to bounds (0,4000000000]. Command execution failed";
                }
            }
            return "Invalid argument type: required long, but String was given";
        } else {
            throw new NoArgumentException();
        }
    }
}
