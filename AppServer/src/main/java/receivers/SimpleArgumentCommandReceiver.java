package receivers;

import packets.CollectionPacket;
import packets.PacketWrapper;

import java.io.Serializable;
import java.util.Vector;
import java.util.stream.Collectors;

import static collection_handling.CollectionManager.dataset;

/**
 * Receiver class<br>
 * Stores realization for commands that have one argument (such as 'filter_by_fuel_consumption fuelConsumption' and 'remove_by_id ID')
 */
public class SimpleArgumentCommandReceiver {
    private final PacketWrapper wrapper = new PacketWrapper();

    /**
     * 'filter_by_fuel_consumption fuelConsumption' command realization
     *
     * @return command execution report (sent to TextReceiver)
     */
    public Serializable filterByFuelConsumption(long fuelConsumption) {
        if (dataset.stream().noneMatch(vehicle -> vehicle.getFuelConsumption() == fuelConsumption)) {
            return wrapper.wrapReport("No elements with the given fuelConsumption value exist");
        }
        return wrapper.wrapCollection(CollectionPacket.PacketIdentifier.COLLECTION, dataset.stream()
                .filter(vehicle -> vehicle.getFuelConsumption() == fuelConsumption)
                .collect(Collectors.toCollection(Vector::new)));
    }

    /**
     * 'remove_by_id ID' command realization
     *
     * @return command execution report (sent to TextReceiver)
     */
    public Serializable removeByID(int id) {
        boolean isFound = dataset.stream()
                .filter(vehicle -> vehicle.getId() == id)
                .findFirst()
                .map(dataset::remove)
                .orElse(false);
        return isFound ? wrapper.wrapReport("Object deleted successfully") : wrapper.wrapReport("There is no object by this ID");
    }

}
