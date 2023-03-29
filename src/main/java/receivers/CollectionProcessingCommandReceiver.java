package receivers;

import collection.CollectionStorage;
import converters.CSVToVectorConverter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Receiver class
 * Stores realization for commands that work with the collection as a whole (such as 'save' and 'clear')
 */
public class CollectionProcessingCommandReceiver {
    /**
     * Stores Vehicle vector
     */
    private final CollectionStorage storage;

    public CollectionProcessingCommandReceiver(CollectionStorage storage) {
        this.storage = storage;
    }

    /**
     * 'clear' command realization
     *
     * @return command execution report (sent to TextReceiver)
     */
    public String clear() {
        storage.getDataSet().removeAllElements();
        return "Collection has been emptied";
    }

    /**
     * 'save' command realization
     *
     * @return command execution report (sent to TextReceiver)
     */
    public String save() throws IOException {
        prepareFile();
        CSVToVectorConverter converter = new CSVToVectorConverter("FILE");
        converter.writeToCSV(storage.getDataSet());
        return "Saved successfully";
    }

    /**
     * Cleans file before saving to it
     * Private method
     */
    private void prepareFile() throws IOException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(System.getenv("FILE"), false)) {
            fileOutputStream.write("".getBytes(StandardCharsets.UTF_8));
        }
    }
}
