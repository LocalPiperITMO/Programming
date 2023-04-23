package receivers;

import collection.CollectionStorage;
import datatype.Vehicle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Receiver class<br>
 * Stores realization for commands that require element building (manual or scripted)
 */
public class CollectionModifyingCommandReceiver {

    private final CollectionStorage storage;

    private Vehicle currentVehicle;

    /**
     * Receives collection from Invoker
     * @param storage contains the collection
     */
    public CollectionModifyingCommandReceiver(CollectionStorage storage) {
        this.storage = storage;
    }

    /**
     * @return collection
     */
    public CollectionStorage getStorage() {
        return storage;
    }

    /**
     * Sets vehicle for modifying commands<br>
     * Vehicle is set outside the receiver
     * @param vehicle pre-built vehicle
     */
    public void setCurrentVehicle(Vehicle vehicle) {
        this.currentVehicle = vehicle;
    }

    /**
     * 'add' command realization
     *
     * @return command execution report (sent to TextReceiver)
     */
    public String addVehicle() {
        storage.getDataSet().add(currentVehicle);
        return "Vehicle added successfully!";
    }
    /**
     * 'add_if_max' command realization
     *
     * @return command execution report (sent to TextReceiver)
     */
    public String addIfMax() {
        Collections.sort(storage.getDataSet());
        if (currentVehicle.compareTo(storage.getDataSet().lastElement()) > 0) {
            storage.getDataSet().add(currentVehicle);
            return "New element added successfully";
        }
        return "New element has not been added: element with ID " + storage.getDataSet().lastElement().getId() + " is greater";
    }
    /**
     * 'remove_greater' command realization
     *
     * @return command execution report (sent to TextReceiver)
     */
    public String removeGreater() {
        List<Vehicle> listOfElementsForRemoval = new ArrayList<>();
        for (Vehicle vehicle : storage.getDataSet()) {
            if (vehicle.compareTo(currentVehicle) > 0) {
                listOfElementsForRemoval.add(vehicle);
            }
        }
        if (listOfElementsForRemoval.isEmpty()) {
            return "No elements were removed";
        } else {
            for (Vehicle vehicle : listOfElementsForRemoval) {
                storage.getDataSet().remove(vehicle);
            }
            return "Greater elements removed successfully";
        }
    }
    /**
     * 'update' command realization
     *
     * @return command execution report (sent to TextReceiver)
     */
    public String update(int id) {
        for (Vehicle vehicleToFind : storage.getDataSet()) {
            if (vehicleToFind.getId() == id) {
                storage.getDataSet().remove(vehicleToFind);
                storage.getIdGenerator().getIdSet().remove(currentVehicle.getId());
                currentVehicle.setId(id);
                storage.getDataSet().add(currentVehicle);
                break;
            }
        }
        return "Vehicle by ID " + id + " updated successfully";
    }
}
