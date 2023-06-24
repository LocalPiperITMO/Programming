package receivers;

import datatypes.Vehicle;
import wrapping.CollectionPacket;
import wrapping.PacketWrapper;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static clienthandling.ClientHandler.concurrentHashMapObserver;


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
        if (concurrentHashMapObserver.map().size() == 0) {
            return wrapper.wrapReport("Dataset is empty: nothing to sort");
        }
        return wrapper.wrapCollection(CollectionPacket.PacketIdentifier.COLLECTION,
                (LinkedHashMap<Integer, ? extends Vehicle>) concurrentHashMapObserver
                .map()
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (v1, v2) -> v1,
                        LinkedHashMap::new
                )));
    }

    /**
     * 'print_field_ascending_fuel_type' command realization
     *
     * @return command execution report (sent to TextReceiver)
     */
    public Serializable printFieldAscendingFuelType() {
        if (concurrentHashMapObserver.map().size() == 0) {
            return wrapper.wrapReport("Dataset is empty: nothing to sort");
        }

        return wrapper.wrapCollection(CollectionPacket.PacketIdentifier.FUEL_TYPE_COLLECTION,
                (LinkedHashMap<Integer, ? extends String>) concurrentHashMapObserver
                .map()
                .entrySet()
                .stream()
                .sorted(Comparator.comparing(entry -> entry.getValue().getFuelType() == null ? 0 : entry.getValue().getFuelType().getPosition()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        (Map.Entry<Integer, Vehicle> entry) -> entry.getValue().getFuelType() == null ? "null" : entry.getValue().getFuelType().name(),
                        (v1, v2) -> v1,
                        LinkedHashMap::new)
                ));
    }

    /**
     * 'reorder' command realization
     *
     * @return command execution report (sent to TextReceiver)
     */
    public Serializable reorder() {
        if (concurrentHashMapObserver.map().size() == 0) {
            return wrapper.wrapReport("Dataset is empty: nothing to sort");
        }
        LinkedHashMap<Integer, Vehicle> reversedMap = concurrentHashMapObserver
                .map()
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey(Collections.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (v1, v2) -> v1,
                        LinkedHashMap::new
                ));
        return wrapper.wrapCollection(CollectionPacket.PacketIdentifier.COLLECTION, reversedMap);
    }

    public Serializable filterByFuelConsumption(long fuelConsumption) {
        if (concurrentHashMapObserver
                .map()
                .entrySet()
                .stream()
                .noneMatch(entry -> entry.getValue().getFuelConsumption() == fuelConsumption)) {
            return wrapper.wrapReport("No elements with the given fuelConsumption value exist");
        }
        return wrapper.wrapCollection(CollectionPacket.PacketIdentifier.COLLECTION,
                (LinkedHashMap<Integer, ? extends Vehicle>) concurrentHashMapObserver
                .map()
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue().getFuelConsumption() == fuelConsumption)
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (v1, v2) -> v1,
                        LinkedHashMap::new)
                ));
    }
}
