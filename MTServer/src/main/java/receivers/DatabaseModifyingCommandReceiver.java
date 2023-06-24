package receivers;

import datatypes.Vehicle;
import wrapping.PacketWrapper;

import java.io.Serializable;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static clienthandling.ClientHandler.concurrentHashMapObserver;
import static clienthandling.ClientHandler.vehicleDatabase;

/**
 * Receiver class<br>
 * Stores realization for commands that require element building (manual or scripted)
 */
public class DatabaseModifyingCommandReceiver {
    private final PacketWrapper wrapper = new PacketWrapper();
    private String username;

    public void setUsername(String username){
        this.username = username;
    }

    /**
     * 'add' command realization
     *
     * @return command execution report (sent to TextReceiver)
     */
    public Serializable addVehicle(Vehicle vehicle) {
        vehicleDatabase.setUsername(username);
        vehicleDatabase.insertElement(vehicle);
        return wrapper.wrapReport("New Vehicle added successfully");
    }

    /**
     * 'add_if_max' command realization
     *
     * @return command execution report (sent to TextReceiver)
     */
    public Serializable addIfMax(Vehicle vehicle) {
        Optional<Map.Entry<Integer, Vehicle>> maxVehicle = concurrentHashMapObserver
                .map()
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue());
        if (maxVehicle.isPresent() && vehicle.compareTo(maxVehicle.get().getValue()) <= 0) {
            return wrapper.wrapReport("New Vehicle has not been added: element with ID " + maxVehicle.get().getKey() + " is greater");
        }
        vehicleDatabase.setUsername(username);
        if (vehicleDatabase.insertElement(vehicle)) {
            return wrapper.wrapReport("New Vehicle added successfully");
        }
        return wrapper.wrapReport("New Vehicle was not added");
    }

    /**
     * 'remove_greater' command realization
     *
     * @return command execution report (sent to TextReceiver)
     */
    public Serializable removeGreater(Vehicle vehicle) {
        ConcurrentHashMap<Integer, Vehicle> mapOfElementsForRemoval = concurrentHashMapObserver
                .map()
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue().compareTo(vehicle) > 0)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (v1, v2) -> v1, ConcurrentHashMap::new));
        if (mapOfElementsForRemoval.isEmpty()) {
            return wrapper.wrapReport("No elements were removed");
        }
        for (Map.Entry<Integer, Vehicle> entrySet : mapOfElementsForRemoval.entrySet()) {
            vehicleDatabase.setUsername(username);
            vehicleDatabase.deleteElement(entrySet.getValue(), entrySet.getKey());
        }
        return wrapper.wrapReport("Greater elements removed successfully");
    }

    /**
     * 'update' command realization
     *
     * @return command execution report (sent to TextReceiver)
     */
    public Serializable update(Vehicle vehicle, int id) {
        Optional<Map.Entry<Integer, Vehicle>> vehicleToUpdate = concurrentHashMapObserver
                .map()
                .entrySet()
                .stream()
                .filter(v -> v.getKey() == id)
                .findFirst();
        if (vehicleToUpdate.isPresent()) {
            vehicleDatabase.setUsername(username);
            if (vehicleDatabase.updateElement(vehicle, id)) {
                return wrapper.wrapReport("Vehicle by ID " + id + " updated successfully");
            }
            return wrapper.wrapReport("Vehicle by ID " + id + " is not owned by user");
        } else {
            return wrapper.wrapReport("There is no vehicle to be updated");
        }
    }

    public Serializable clear() {
        if (concurrentHashMapObserver.map().size() != 0) {
            for (Map.Entry<Integer, Vehicle> entrySet : concurrentHashMapObserver.map().entrySet()) {
                vehicleDatabase.setUsername(username);
                vehicleDatabase.deleteElement(entrySet.getValue(), entrySet.getKey());
            }
            return wrapper.wrapReport("Collection has been emptied");
        }
        return wrapper.wrapReport("Collection is already empty");
    }

    public Serializable removeByID(int id) {
        Optional<Map.Entry<Integer, Vehicle>> vehicleToRemove = concurrentHashMapObserver.map().entrySet().stream()
                .filter(e -> e.getKey() == id)
                .findFirst();
        if (vehicleToRemove.isPresent()) {
            vehicleDatabase.setUsername(username);
            if (vehicleDatabase.deleteElement(vehicleToRemove.get().getValue(), vehicleToRemove.get().getKey())) {
                return wrapper.wrapReport("Object deleted successfully");
            }
            return wrapper.wrapReport("User does not own object by this ID");
        }
        return wrapper.wrapReport("There is no object by this ID");
    }
}
