package collection_handling;

import data.Vehicle;
import receivers.TextReceiver;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CollectionLoader {
    private final TextReceiver receiver = new TextReceiver();
    public List<Vehicle> loadCollection(String envVar) {
        Optional<File> hypotheticalFile = openFileWithPathFromEnv(envVar);
        if (hypotheticalFile.isPresent()) {
            CollectionParser parser = new CollectionParser();
            receiver.printToLog(this.getClass().getSimpleName(),"Retrieving collection...");
            return parser.parseToVector(hypotheticalFile.get());
        }
        receiver.printToLog(this.getClass().getSimpleName(),"File not found. Creating empty collection...");
        return new ArrayList<>();
    }

    private Optional<File> openFileWithPathFromEnv(String envVar) {
        receiver.printToLog(this.getClass().getSimpleName(),"Opening file...");
        String path = System.getenv(envVar);
        if (path != null) {
            if (path.trim().length() != 0) {
                return Optional.of(new File(path));
            } else {
                return Optional.empty();
            }
        } else {
            return Optional.empty();
        }
    }
}
