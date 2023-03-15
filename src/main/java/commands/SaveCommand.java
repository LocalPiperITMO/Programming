package commands;

import converters.VehicleVectorToStringListConverter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class SaveCommand implements Command {
    private final Receiver receiver;
    private final VehicleVectorToStringListConverter converter;

    /**
     * "save" command
     * Saves the collection to the file it was taken from
     *
     * @param receiver used for storing both the collection and the path to file
     */
    public SaveCommand(Receiver receiver) {
        this.receiver = receiver;
        this.converter = new VehicleVectorToStringListConverter(receiver.dataSet());
    }

    /**
     * Used for showing information.
     *
     * @return information about the command
     */
    public String showInfo() {
        return "No argument required. Saves the dataset to file";
    }

    /**
     * Used for cleaning the file in order to save to it
     *
     * @throws IOException if unexpected error occurs
     */
    private void prepareFile() throws IOException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(receiver.varAddress().toString(), false)) {
            fileOutputStream.write("".getBytes(StandardCharsets.UTF_8));
        }
    }

    /**
     * Executes command
     *
     * @param arg              command argument
     * @param isCalledByScript checks if command called from script
     * @throws IOException if unexpected error occurs
     */
    public void execute(String arg, boolean isCalledByScript) throws IOException {
        prepareFile();
        converter.setDataSet(receiver.dataSet());
        List<String> listOfStrings = converter.convertVehicleVectorToStringList();
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

