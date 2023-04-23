package receivers;

import collection.CollectionStorage;
import datatype.Vehicle;

/**
 * Receiver class<br>
 * Stores realization for commands that have one argument (such as 'filter_by_fuel_consumption fuelConsumption' and 'remove_by_id ID')
 */
public class SimpleArgumentCommandReceiver {
    /**
     * Stores Vehicle vector
     */
    private final CollectionStorage storage;

    /**
     * Receives collection
     * @param storage contains the collection
     */
    public SimpleArgumentCommandReceiver(CollectionStorage storage) {
        this.storage = storage;
    }

    /**
     * 'filter_by_fuel_consumption fuelConsumption' command realization
     *
     * @return command execution report (sent to TextReceiver)
     */
    public String filterByFuelConsumption(long fuelConsumption) {
        StringBuilder report = new StringBuilder(String.format("%7s " + "%-20s " + "%12s " + "%15s " + "%11s " + "%15s " + "%15s " + "%-10s " + "%-10s\n",
                "ID",
                "Name",
                "CreationDate",
                "X",
                "Y",
                "EnginePower",
                "FuelConsumption",
                "Type",
                "FuelType"));
        int count = 0;
        for (Vehicle vehicle : storage.getDataSet()) {
            if (fuelConsumption == vehicle.getFuelConsumption()) {
                ++count;
                report.append(vehicle).append("\n");
            }
        }
        if (count == 0) {
            return "No elements with the given fuelConsumption value exist";
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
