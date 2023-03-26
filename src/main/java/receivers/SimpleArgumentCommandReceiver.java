package receivers;

import collection.CollectionStorage;
import datatype.Vehicle;

public class SimpleArgumentCommandReceiver {
    private final CollectionStorage storage;

    public SimpleArgumentCommandReceiver(CollectionStorage storage) {
        this.storage = storage;
    }

    public String filterByFuelConsumption(long fuelConsumption) {
        StringBuilder report = new StringBuilder("ID Name CreationDate X Y EnginePower FuelConsumption Type FuelType\n");
        for (Vehicle vehicle : storage.getDataSet()) {
            if (fuelConsumption == vehicle.getFuelConsumption()) {
                report.append(vehicle).append("\n");
            }
        }
        return String.valueOf(report);
    }

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
