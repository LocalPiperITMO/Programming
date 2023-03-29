package receivers;

import collection.CollectionStorage;
import datatype.Vehicle;

/**
 * Receiver class
 * Stores realization for commands that have one argument (such as 'filter_by_fuel_consumption fuelConsumption' and 'remove_by_id ID')
 */
public class SimpleArgumentCommandReceiver {
    /**
     * Stores Vehicle vector
     */
    private final CollectionStorage storage;

    public SimpleArgumentCommandReceiver(CollectionStorage storage) {
        this.storage = storage;
    }
    /**
     * 'filter_by_fuel_consumption fuelConsumption' command realization
     *
     * @return command execution report (sent to TextReceiver)
     */
    public String filterByFuelConsumption(long fuelConsumption) {
        StringBuilder report = new StringBuilder("ID Name CreationDate X Y EnginePower FuelConsumption Type FuelType\n");
        for (Vehicle vehicle : storage.getDataSet()) {
            if (fuelConsumption == vehicle.getFuelConsumption()) {
                report.append(vehicle).append("\n");
            }
        }
        return String.valueOf(report);
    }
    /**
     * 'remove_by_id ID' command realization
     *
     * @return command execution report (sent to TextReceiver)
     */
    public String removeByID(int id) {
        boolean isFound;
        for (Vehicle vehicle : storage.getDataSet()) {
            isFound = (vehicle.getId() == id);
            if (isFound) {
                storage.getDataSet().remove(vehicle);
                return "Object deleted successfully";
            }
        }
        return "There is no object by this ID.";
    }

}
