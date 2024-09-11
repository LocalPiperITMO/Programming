package receivers;

import collection.CollectionStorage;
import converters.CSVToVectorConverter;

import java.io.IOException;

/**
 * Receiver class<br>
 * Stores realization for commands that work with the collection as a whole (such as 'save' and 'clear')
 */
public class CollectionProcessingCommandReceiver {

    private final CollectionStorage storage;

    private final CSVToVectorConverter converter;

    /**
     * Receives storage from Invoker<br>
     * Creates converter object to perform "save" command
     * @param storage contains the collection
     */
    public CollectionProcessingCommandReceiver(CollectionStorage storage) {
        this.storage = storage;
        this.converter = new CSVToVectorConverter();
    }

    /**
     * 'clear' command realization
     *
     * @return command execution report (sent to TextReceiver)
     */
    public String clear() {
        if (storage.getDataSet().size()!=0) {
            storage.getDataSet().removeAllElements();
            return "Collection has been emptied";
        }
        return "Collection is already empty";
    }

    /**
     * 'save' command realization
     *
     * @return command execution report (sent to TextReceiver)
     */
    public String save() throws IOException {
        converter.saveToFile(storage.getDataSet());
        return "Saved successfully";
    }
}
