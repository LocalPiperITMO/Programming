package receivers;

import collection.CollectionStorage;
import datatype.Vehicle;

public class SimpleArgumentCommandReceiver {
    private final CollectionStorage storage;

    public SimpleArgumentCommandReceiver(CollectionStorage storage) {
        this.storage = storage;
    }

    public void filterByFuelConsumption(long fuelConsumption) {
        System.out.println("ID Name CreationDate X Y EnginePower FuelConsumption Type FuelType");
        for (Vehicle vehicle : storage.getDataSet()) {
            if (fuelConsumption == vehicle.getFuelConsumption()) {
                System.out.println(vehicle);
            }
        }
    }

    public void removeByID(int id) {
        boolean isFound = false;
        for (Vehicle vehicle : storage.getDataSet()) {
            isFound = (vehicle.getId() == id);
            if (isFound) {
                storage.getDataSet().remove(vehicle);
                System.out.println("Object deleted successfully");
                break;
            }
        }
        if (!isFound) {
            System.out.println("There is no object by this ID.");
        }
    }

}
