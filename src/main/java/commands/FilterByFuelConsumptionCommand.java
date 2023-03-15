package commands;

import datatype.Vehicle;
import exceptions.NoArgumentException;

public class FilterByFuelConsumptionCommand implements Command {
    private final Receiver receiver;

    public FilterByFuelConsumptionCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    public String showInfo() {
        return "Required argument - fuelConsumption(number). Displays elements with the given fuelConsumption";
    }

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
