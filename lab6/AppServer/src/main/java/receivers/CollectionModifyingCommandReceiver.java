package receivers;

import collection_handling.CollectionIDGenerator;
import data.Vehicle;
import packets.PacketWrapper;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static collection_handling.CollectionManager.dataset;

/**
 * Receiver class<br>
 * Stores realization for commands that require element building (manual or scripted)
 */
public class CollectionModifyingCommandReceiver {
    private final PacketWrapper wrapper = new PacketWrapper();

    /**
     * 'add' command realization
     *
     * @return command execution report (sent to TextReceiver)
     */
    public Serializable addVehicle(Vehicle vehicle) {
        vehicle.setId(CollectionIDGenerator.generateID());
        dataset.add(vehicle);
        return wrapper.wrapReport("Vehicle added successfully!");
    }

    /**
     * 'add_if_max' command realization
     *
     * @return command execution report (sent to TextReceiver)
     */
    public Serializable addIfMax(Vehicle vehicle) {
        Optional<Vehicle> maxVehicle = dataset.stream()
                .max(Comparator.naturalOrder());
        if (maxVehicle.isPresent() && vehicle.compareTo(maxVehicle.get()) <= 0) {
            return wrapper.wrapReport("New element has not been added: element with ID " + maxVehicle.get().getId() + " is greater");
        }
        vehicle.setId(CollectionIDGenerator.generateID());
        dataset.add(vehicle);
        return wrapper.wrapReport("New element added successfully");
    }

    /**
     * 'remove_greater' command realization
     *
     * @return command execution report (sent to TextReceiver)
     */
    public Serializable removeGreater(Vehicle vehicle) {
        List<Vehicle> listOfElementsForRemoval = dataset.stream()
                .filter(currentVehicle -> currentVehicle.compareTo(vehicle) > 0)
                .toList();
        if (listOfElementsForRemoval.isEmpty()) {
            return wrapper.wrapReport("No elements were removed");
        }
        dataset.removeAll(listOfElementsForRemoval);
        return wrapper.wrapReport("Greater elements removed successfully");
    }

    /**
     * 'update' command realization
     *
     * @return command execution report (sent to TextReceiver)
     */
    public Serializable update(Vehicle vehicle, int id) {
        Optional<Vehicle> vehicleToUpdate = dataset.stream()
                .filter(v -> v.getId() == id)
                .findFirst();
        if (vehicleToUpdate.isPresent()) {
            int index = dataset.indexOf(vehicleToUpdate.get());
            vehicle.setId(id);
            dataset.set(index, vehicle);
            return wrapper.wrapReport("Vehicle by ID " + id + " updated successfully");
        } else {
            return wrapper.wrapReport("There is no vehicle to be updated");
        }
    }

    public Serializable clear() {
        if (dataset.size() != 0) {
            dataset.removeAllElements();
            return wrapper.wrapReport("Collection has been emptied");
        }
        return wrapper.wrapReport("Collection is already empty");
    }
}
