package commands;

import receivers.SortingCommandReceiver;

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
        SortingCommandReceiver receiver = new SortingCommandReceiver();
        return receiver.filterByFuelConsumption(fuelConsumption);
    }
}
