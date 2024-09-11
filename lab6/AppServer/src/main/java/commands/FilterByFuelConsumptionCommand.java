package commands;

import receivers.SimpleArgumentCommandReceiver;

import java.io.Serializable;

/**
 * Command for printing Vehicles that match the condition (if Vehicle's fuelConsumption is equal to the given)
 */
public class FilterByFuelConsumptionCommand implements Command {

    private final long fuelConsumption;

    public FilterByFuelConsumptionCommand(long fuelConsumption) {
        this.fuelConsumption = fuelConsumption;
    }




    public Serializable execute() {
        SimpleArgumentCommandReceiver receiver = new SimpleArgumentCommandReceiver();
        return receiver.filterByFuelConsumption(fuelConsumption);
    }
}
