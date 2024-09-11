package receivers;

import commands.CommandInfoConnector;
import packets.CollectionPacket;
import packets.PacketWrapper;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Map;
import java.util.stream.Collectors;

import static collection_handling.CollectionManager.dataset;

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
        Map<String, String> commandInfoMap = new CommandInfoConnector().getCommandInfoMap();
        return wrapper.wrapCollection(CollectionPacket.PacketIdentifier.COMMAND_INFO, commandInfoMap.entrySet()
                .stream()
                .map(entry -> String.format("%-31s : %s", entry.getKey(), entry.getValue()))
                .collect(Collectors.toList()));
    }

    /**
     * 'info' command realization
     *
     * @return command execution report (sent to TextReceiver)
     */
    public Serializable info() {
        return wrapper.wrapReport(String.format("%-26s : " + dataset.getClass().getSimpleName() +
                "\n%-26s : " + LocalDate.now() +
                "\n%-26s : " + dataset.size(), "Dataset type", "Dataset creation date", "Dataset number of elements"));
    }

    /**
     * 'show' command realization
     *
     * @return command execution report (sent to TextReceiver)
     */

    public Serializable show() {
        if (dataset.size() == 0) {
            return wrapper.wrapReport("Dataset is empty: nothing to show");
        }
        return wrapper.wrapCollection(CollectionPacket.PacketIdentifier.COLLECTION, dataset);
    }
}