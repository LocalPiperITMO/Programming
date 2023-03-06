package commands;

import converters.VehicleVectorToStringListConverter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class SaveCommand implements Command {
    private final Receiver receiver;
    private final VehicleVectorToStringListConverter converter;

    public SaveCommand(Receiver receiver) {
        this.receiver = receiver;
        this.converter = new VehicleVectorToStringListConverter(receiver.dataSet());
    }

    public void prepareFile() throws IOException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(receiver.varAddress().toString(), false)) {
            fileOutputStream.write("".getBytes(StandardCharsets.UTF_8));
        }
    }

    public void execute(String arg) throws IOException {
        prepareFile();
        converter.setDataSet(receiver.dataSet());
        List<String> listOfStrings = converter.convertObjectVectorToStringList();
        try (FileOutputStream fileOutputStream = new FileOutputStream(receiver.varAddress().toString(), true)) {
            for (String line : listOfStrings) {
                byte[] buffer = line.getBytes(StandardCharsets.UTF_8);
                fileOutputStream.write(buffer, 0, buffer.length);
                fileOutputStream.write("\n".getBytes(StandardCharsets.UTF_8));
            }
        }
        System.out.println("Saved successfully");
    }

}

