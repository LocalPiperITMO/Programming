package receivers;

import datatypes.Vehicle;
import wrapping.CollectionPacket;
import wrapping.PacketWrapper;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static commandlistmanagement.CommandListPreparer.commandInfoConnector;
import static clienthandling.ClientHandler.concurrentHashMapObserver;

/**
 * Receiver class<br>
 * Stores realization for displaying commands (such as 'help', 'show' and 'info')
 */
public class DisplayingCommandReceiver {
    private final PacketWrapper wrapper = new PacketWrapper();

    /**
     * 'help' command realization
     *
     * @return command execution report (sent to TextReceiver)
     */
    public Serializable help() {
        int index = 1;
        LinkedHashMap<Integer, String> helpMap = new LinkedHashMap<>();
        for (Map.Entry<String, String> entry : commandInfoConnector.entrySet()) {
            helpMap.put(index, entry.getKey() + " " + entry.getValue());
            ++index;
        }

        return wrapper.wrapCollection(CollectionPacket.PacketIdentifier.COMMAND_INFO, helpMap);
    }

    /**
     * 'info' command realization
     *
     * @return command execution report (sent to TextReceiver)
     */
    public Serializable info() {
        return wrapper.wrapReport(String.format("%-26s : " +
                concurrentHashMapObserver.map().getClass().getSimpleName() +
                "\n%-26s : " + LocalDate.now() +
                "\n%-26s : " + concurrentHashMapObserver.map().size(), "Dataset type", "Dataset creation date", "Dataset number of elements"));
    }

    /**
     * 'show' command realization
     *
     * @return command execution report (sent to TextReceiver)
     */

    public Serializable show() {
        if (concurrentHashMapObserver.map().size() == 0) {
            return wrapper.wrapReport("Dataset is empty: nothing to show");
        }
        LinkedHashMap<Integer, Vehicle> sortedMap = concurrentHashMapObserver
                .map()
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (v1, v2) -> v1,
                        LinkedHashMap::new
                ));
        return wrapper.wrapCollection(CollectionPacket.PacketIdentifier.COLLECTION, sortedMap);
    }
}