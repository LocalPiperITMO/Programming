package receivers;

import collection.CollectionStorage;
import datatype.Vehicle;

import java.util.Collections;

public class CollectionModifyingCommandReceiver {
    /**
     * Stores Vehicle vector
     */
    private final CollectionStorage storage;
    private Vehicle currentVehicle;

    public CollectionModifyingCommandReceiver(CollectionStorage storage) {
        this.storage = storage;
    }

    public void setCurrentVehicle(Vehicle vehicle) {
        this.currentVehicle = vehicle;
    }

    public String addVehicle() {
        storage.getDataSet().add(currentVehicle);
        return "Vehicle added successfully!";
    }

    public String addIfMax() {
        Collections.sort(storage.getDataSet());
        if (currentVehicle.compareTo(storage.getDataSet().lastElement()) > 0) {
            storage.getDataSet().add(currentVehicle);
            return "New element added successfully";
        }
        return "New element has not been added: element with ID " + storage.getDataSet().lastElement().getId() + " is greater";
    }

    public String removeGreater() {
        storage.getDataSet().removeIf(vehicleToCompare -> vehicleToCompare.compareTo(currentVehicle) > 0);
        return "Greater elements removed successfully";
    }

    public String update(int id) {
        boolean isFound = false;
        for (Vehicle vehicleToFind : storage.getDataSet()) {
            if (vehicleToFind.getId() == id) {
                vehicleToFind = currentVehicle;
                vehicleToFind.setId(id);
                isFound = true;
                break;
            }
        }
        if (!isFound) {
            storage.getDataSet().add(currentVehicle);
            return "Element with given ID does not exist. New Vehicle with the given ID was added to the collection";
        }
        return "Vehicle by ID " + id + " updated successfully";
    }
}
