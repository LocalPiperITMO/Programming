package collection_handling;

import com.opencsv.CSVWriter;
import packets.PacketWrapper;
import packets.ReportPacket;
import receivers.TextReceiver;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CollectionSaver {
    private final TextReceiver receiver = new TextReceiver();

    public ReportPacket save(String fileName) {
        try {
            String path = System.getenv(fileName);
            if (path == null) {
                receiver.printToLog(this.getClass().getSimpleName(), "Couldn't access file");
                File file = new File("output.csv");
                try (CSVWriter writer = new CSVWriter(new FileWriter(file, true))) {
                    receiver.printToLog(this.getClass().getSimpleName(), "Saving collection to new file...");
                    writer.writeAll(new CollectionParser().parseToStringList());
                    receiver.printToLog(this.getClass().getSimpleName(), "Collection saved");
                    return new PacketWrapper().wrapReport("Goodbye");
                }
            }
            File file = new File(path);
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) {
                writer.write("");
            }
            try (CSVWriter writer = new CSVWriter(new FileWriter(file, true))) {
                receiver.printToLog(this.getClass().getSimpleName(), "Saving collection...");
                writer.writeAll(new CollectionParser().parseToStringList());
                receiver.printToLog(this.getClass().getSimpleName(), "Collection saved");
                return new PacketWrapper().wrapReport("Goodbye");
            }
        } catch (IOException e) {
            return new PacketWrapper().wrapReport("Something went wrong trying to save");
        }

    }
}
