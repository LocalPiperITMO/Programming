package receivers;

import data.Vehicle;
import packets.CollectionPacket;
import packets.PacketWrapper;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;
import java.util.stream.Collectors;

import static collection_handling.CollectionManager.dataset;

/**
 * Receiver class<br>
 * Stores realization for sorting commands (such as 'print_ascending', 'print_field_ascending_fuel_type' and 'reorder')
 */
public class SortingCommandReceiver {
    private final PacketWrapper wrapper = new PacketWrapper();


    /**
     * 'print_ascending' command realization
     *
     * @return command execution report (sent to TextReceiver)
     */
    public Serializable printAscending() {
        if (dataset.size() == 0) {
            return wrapper.wrapReport("Dataset is empty: nothing to sort");
        }
        return wrapper.wrapCollection(CollectionPacket.PacketIdentifier.COLLECTION, dataset.stream()
                .sorted()
                .collect(Collectors.toCollection(Vector::new)));
    }

    /**
     * 'print_field_ascending_fuel_type' command realization
     *
     * @return command execution report (sent to TextReceiver)
     */
    public Serializable printFieldAscendingFuelType() {
        if (dataset.size() == 0) {
            return wrapper.wrapReport("Dataset is empty: nothing to sort");
        }

        return wrapper.wrapCollection(CollectionPacket.PacketIdentifier.FUEL_TYPE_COLLECTION, dataset.stream()
                .sorted(Comparator.comparing(vehicle -> vehicle.getFuelType() == null ? 0 : vehicle.getFuelType().getPosition()))
                .collect(Collectors.toCollection(Vector::new)));
    }

    /**
     * 'reorder' command realization
     *
     * @return command execution report (sent to TextReceiver)
     */
    public Serializable reorder() {
        if (dataset.size() == 0) {
            return wrapper.wrapReport("Dataset is empty: nothing to sort");
        }
        Vector<Vehicle> reversedDataset = new Vector<>(dataset);
        Collections.reverse(reversedDataset);
        return wrapper.wrapCollection(CollectionPacket.PacketIdentifier.COLLECTION, reversedDataset);
    }
}
