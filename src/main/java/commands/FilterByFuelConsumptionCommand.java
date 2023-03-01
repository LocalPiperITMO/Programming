package commands;

import datatype.Vehicle;
import exceptions.NoArgumentException;
import pattern.Command;
import pattern.Receiver;

public class FilterByFuelConsumptionCommand implements Command {
    private final Receiver receiver;

    public FilterByFuelConsumptionCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    public void execute(String argument) throws NoArgumentException {
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
