package commands;

import datatype.Vehicle;
import exceptions.NoArgumentException;

public class FilterByFuelConsumptionCommand implements Command {
    private final Receiver receiver;

    /**
     * "filter_by_fuel_consumption fuel_consumption" command.
     * Deletes all elements with the fuel_consumption value lower than one given as argument from the collection
     *
     * @param receiver used for storing the collection
     */
    public FilterByFuelConsumptionCommand(Receiver receiver) {
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
     * @param argument         command argument
     * @param isCalledByScript checks if command called from script
     * @throws NoArgumentException if command requires argument but none were given
     */
    public void execute(String argument, boolean isCalledByScript) throws NoArgumentException {
        if (checkIfUserInputMatchesRequiredArgument(argument, true)) {
            long fuelConsumption = Long.parseLong(argument);
            System.out.println("ID Name CreationDate X Y EnginePower FuelConsumption Type FuelType");
            for (Vehicle vehicle : receiver.dataSet()) {
                if (fuelConsumption == vehicle.getFuelConsumption()) {
                    System.out.println(vehicle);
                }
            }
        } else {
            throw new NoArgumentException();
        }
    }
}
