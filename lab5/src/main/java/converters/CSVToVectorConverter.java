package converters;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import datatype.Vehicle;
import exceptions.*;
import generators.IDGenerator;
import receivers.TextReceiver;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Vector;

/**
 * Converter class where every operation regarding information channeling from files to collections and backwards occur.
 */
public class CSVToVectorConverter {
    private final String fileName;
    private final TextReceiver textReceiver;
    private final IDGenerator idGenerator = new IDGenerator();

    /**
     * Gets environment variable named "FILE" and creates receiver for text output
     */
    public CSVToVectorConverter() {
        this.fileName = "FILE";
        this.textReceiver = new TextReceiver();
    }

    /**
     * @return IDGenerator
     */
    public IDGenerator getIdGenerator() {
        return idGenerator;
    }

    /**
     * @return vector of Vehicles
     */
    public Vector<Vehicle> getVector() throws InvalidPathException, IOException {
        CSVtoStringListConverter converter = new CSVtoStringListConverter();
        return (new StringListToVehicleVectorConverter()).convertStringListToVector(converter.convertCSVtoStringList());
    }

    /**
     * Operation of saving current collection to a previously opened file<br>
     * If the file was not read from before (for some reason), creates file "output.csv" and saves it here
     * @param dataSet contains the collection
     * @throws IOException if unexpected error occurs
     */
    public void saveToFile(Vector<Vehicle> dataSet) throws IOException {
        (new FileSaver(dataSet)).writeToCSV();
    }
    private class FileSaver {
        private final Vector<Vehicle> dataSet;

        /**
         * Installs collection for means of saving
         * @param dataSet contains the collection
         */
        public FileSaver(Vector<Vehicle> dataSet) {
            this.dataSet = dataSet;
        }

        private List<String[]> convertVehicleVectorToStringList() {
            //Opel,124,4,51,21,CHOPPER,MANPOWER
            List<String[]> stringList = new ArrayList<>();
            for (Vehicle vehicle : dataSet) {
                String type = (vehicle.getType() == null) ? "" : String.valueOf(vehicle.getType());
                String fuelType = (vehicle.getFuelType() == null) ? "" : String.valueOf(vehicle.getFuelType());
                String[] line = {
                        vehicle.getName(),
                        String.valueOf(vehicle.getCoordinates().getX()),
                        String.valueOf(vehicle.getCoordinates().getY()),
                        String.valueOf(vehicle.getEnginePower()),
                        String.valueOf(vehicle.getFuelConsumption()),
                        type,
                        fuelType
                };
                stringList.add(line);
            }

            return stringList;
        }

        private void writeToCSV() throws IOException {
            try {
                writeToExistingFileElseWriteToNewOne();
            } catch (FileNotFoundException fileNotFoundException) {
                writeToNewFile();
            }

        }

        private void writeToExistingFileElseWriteToNewOne() throws IOException {
            if (getFileWithPathFromEnvIfPathNotNull(fileName).isPresent()) {
                try {
                    throwErrorIfFileNotExist(System.getenv(fileName));
                    prepareFileElseThrowErrorIfNotWriteable(getFileWithPathFromEnvIfPathNotNull(fileName).get());
                    try (CSVWriter writer = new CSVWriter(new FileWriter(getFileWithPathFromEnvIfPathNotNull(fileName).get(), true))) {
                        writer.writeAll(convertVehicleVectorToStringList());
                    }
                } catch (UnmodifiableFileException unmodifiableFileException) {
                    writeToNewFile();
                }
            } else {
                throw new FileNotFoundException();
            }
        }

        private void writeToNewFile() throws IOException {
            File file = createFileToSaveTo();
            try (CSVWriter writer = new CSVWriter(new FileWriter(file, true))) {
                writer.writeAll(convertVehicleVectorToStringList());
            }
        }

        private void checkIfFileWritableElseThrowError(File file) throws UnmodifiableFileException {
            if (!Files.isWritable(file.toPath())) {
                sendMessage("Cannot save to file: access denied");
                throw new UnmodifiableFileException();
            }

        }

        private File createFileToSaveTo() throws IOException {
            Path outputPath = Path.of("output.csv");
            if (!Files.exists(outputPath)) {
                sendMessage("New file created. Saving to new file...");
            } else {
                Files.delete(outputPath);
                sendMessage("File \"output.csv\" already exists. Saving to this file...");
            }
            return new File(String.valueOf(outputPath));
        }

        private void prepareFileElseThrowErrorIfNotWriteable(File file) throws UnmodifiableFileException, IOException {
            checkIfFileWritableElseThrowError(file);
            try (FileOutputStream writer = new FileOutputStream(file, false)) {
                writer.write("".getBytes(StandardCharsets.UTF_8));
            }
        }

    }

    private void throwErrorIfFileNotExist(String envValue) throws FileNotFoundException {
        if (Files.notExists(Path.of(envValue))) {
            throw new FileNotFoundException();
        }
    }

    private class CSVtoStringListConverter {
        private List<String[]> convertCSVtoStringList() throws InvalidPathException, IOException {
            try {
                throwErrorIfFileNotExist(System.getenv(fileName));
                return readCSVFromFileAndReturnStringListElseThrowError(createFileIfExistsAndReadableElseThrowError());
            } catch (NullPointerException nullPointerException) {
                sendMessage("Error while trying to read environment variable. Check if the path/variable name is correct");
                throw new InvalidPathException();
            } catch (FileNotFoundException fileNotFoundException) {
                sendMessage("File does not exist");
                throw new FileNotFoundException();
            } catch (UnreadableFileException unreadableFileException) {
                return createNewStringList();
            }
        }

        private List<String[]> createNewStringList() {
            sendMessage("Creating empty collection");
            return new ArrayList<>();
        }

        private File createFileIfExistsAndReadableElseThrowError() throws FileNotFoundException, UnreadableFileException {
            if (getFileWithPathFromEnvIfPathNotNull(fileName).isPresent()) {
                File file = getFileWithPathFromEnvIfPathNotNull(fileName).get();
                throwErrorIfFileNotReadable(file);
                return file;
            }
            throw new FileNotFoundException();
        }

        private void throwErrorIfFileNotReadable(File file) throws UnreadableFileException {
            if (!Files.isReadable(file.toPath())) {
                sendMessage("Cannot read file: access denied");
                throw new UnreadableFileException();
            }
        }

        private List<String[]> readCSVFromFileAndReturnStringListElseThrowError(File file) throws IOException {
            try (CSVReader csvReader = new CSVReader(new InputStreamReader(new FileInputStream(file)))) {
                return csvReader.readAll();
            } catch (FileNotFoundException e) {
                sendMessage("File not found");
                throw new IOException();
            } catch (IOException | CsvException e) {
                sendMessage("Something wrong with your CSV-file. Loading failed");
                throw new IOException();
            }

        }
    }

    private class StringListToVehicleVectorConverter {
        private final Vector<Vehicle> dataset;
        private int corruptedLinesCounter;
        private int lineCounter;

        public StringListToVehicleVectorConverter() {
            this.dataset = new Vector<>();
            this.corruptedLinesCounter = 0;
            this.lineCounter = 0;
        }

        private boolean checkIfEmptyStringList(List<String[]> strings) {
            return strings.size() == 0;
        }

        private void increaseLineCounter() {
            ++this.lineCounter;
        }

        private void increaseCorruptedLinesCounter() {
            ++this.corruptedLinesCounter;
        }

        private void createVectorAndFillWithData(List<String[]> buildingMaterial) {
            for (String[] line : buildingMaterial) {
                increaseLineCounter();
                Optional<Vehicle> vehicle = buildVehicleIfPossible(line);
                if (vehicle.isPresent()) {
                    this.dataset.add(vehicle.get());
                } else {
                    sendMessage("Vehicle creation failed");
                }
            }

        }

        private Optional<Vehicle> buildVehicleIfPossible(String[] parameters) {
            try {
                if (checkIfArrayOfRequiredSize(parameters)) {
                    Vehicle vehicle = new Vehicle();
                    vehicle.setName(parameters[0])
                            .getCoordinates()
                            .setX(parameters[1])
                            .setY(parameters[2]);
                    vehicle.setEnginePower(parameters[3])
                            .setFuelConsumption(parameters[4])
                            .setType(parameters[5])
                            .setFuelType(parameters[6])
                            .setId(idGenerator.generateRandomID());
                    return Optional.of(vehicle);
                } else {
                    sendMessage("Line " + lineCounter + " has incorrect number of arguments");
                    increaseCorruptedLinesCounter();
                }
            } catch (NoArgumentException e) {
                sendMessage("Line " + lineCounter + " has no argument");
                increaseCorruptedLinesCounter();
            } catch (LessOrEqualToZeroException e) {
                sendMessage("Line " + lineCounter + " has an argument that is less or equal to zero");
                increaseCorruptedLinesCounter();
            } catch (NumberFormatException e) {
                sendMessage("Line " + lineCounter + " has a string value instead of numeric one");
                increaseCorruptedLinesCounter();
            } catch (IllegalArgumentException e) {
                sendMessage("Line " + lineCounter + " has an illegal VehicleType/FuelType value.");
                increaseCorruptedLinesCounter();
            }
            return Optional.empty();
        }

        private boolean checkIfArrayOfRequiredSize(String[] line) {
            return line.length == 7;
        }

        private Vector<Vehicle> convertStringListToVector(List<String[]> text) {
            if (!checkIfEmptyStringList(text)) {
                createVectorAndFillWithData(text);
                sendMessage("Dataset is ready for use. Number of corrupted lines: " + corruptedLinesCounter);
                return this.dataset;
            }
            return new Vector<>();
        }

    }

    /**
     * Gets environment variable name and tries to find the file with the path stored in the variable<br>
     * If the path is null, does not return anything
     * @param fileName environment variable name
     * @return file if found, otherwise nothing
     */
    Optional<File> getFileWithPathFromEnvIfPathNotNull(String fileName) {
        String path = System.getenv(fileName);
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

    private void sendMessage(String message) {
        textReceiver.print(message);
    }
}
