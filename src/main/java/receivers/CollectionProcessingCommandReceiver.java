package receivers;

import collection.CollectionStorage;
import converters.CSVToVectorConverter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class CollectionProcessingCommandReceiver {
    private final CollectionStorage storage;

    public CollectionProcessingCommandReceiver(CollectionStorage storage) {
        this.storage = storage;
    }

    public void clear() {
        storage.getDataSet().removeAllElements();
        System.out.println("Collection has been emptied");
    }

    public void save() throws IOException {
        prepareFile();
        CSVToVectorConverter converter = new CSVToVectorConverter("FILE");
        converter.writeToCSV(storage.getDataSet());
        System.out.println("Saved successfully");
    }

    private void prepareFile() throws IOException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(System.getenv("FILE"), false)) {
            fileOutputStream.write("".getBytes(StandardCharsets.UTF_8));
        }
    }
}
