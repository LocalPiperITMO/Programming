package converters;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import datatype.Vehicle;
import exceptions.LessOrEqualToZeroException;
import exceptions.NoArgumentException;
import generators.IDGenerator;
import receivers.TextReceiver;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Vector;

/**
 * Converter that can turn CSV file to Vector and Vector to CSV file
 */
public class CSVToVectorConverter {
    /**
     * Used when writing to file
     */
    private final String fileName;
    /**
     * Used as an output stream
     */
    private final TextReceiver textReceiver;
    /**
     * Used when generating Vehicles from CSV file
     */
    private final IDGenerator idGenerator = new IDGenerator();

    public CSVToVectorConverter(String fileName) {
        this.fileName = fileName;
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
     * @throws CsvException if there is a problem parsing CSV file
     */
    public Vector<Vehicle> getVector() throws IOException, CsvException {
        return convertStringListToVehicleVector(convertCSVtoStringList(this.fileName));
    }

    /**
     * Writes collection to CSV file
     * @param dataSet vector of Vehicles
     */
    public void writeToCSV(Vector<Vehicle> dataSet) throws IOException {
        FileWriter writer;
        if (openFileWithPathFromEnv(fileName).isPresent()) {
            writer = new FileWriter(openFileWithPathFromEnv(fileName).get(), true);
            CSVWriter csvWriter = new CSVWriter(writer);
            csvWriter.writeAll(convertVehicleVectorToStringList(dataSet));
            csvWriter.close();
            writer.close();
        } else {
            throw new FileNotFoundException("BITCH WORK PLEASE");
        }

    }

    /**
     * Converts CSV to list of String arrays (used for creating vector of Vehicles).
     * Private method
     * @param fileName name of file the list is taken from
     * @return list of String arrays (arguments for Vehicle building)
     * @throws CsvException if there is a problem parsing CSV file
     */
    private List<String[]> convertCSVtoStringList(String fileName) throws IOException, CsvException {
        InputStreamReader reader;
        if (openFileWithPathFromEnv(fileName).isPresent()) {
            reader = new InputStreamReader(new FileInputStream(openFileWithPathFromEnv(fileName).get()));
            CSVReader csvReader = new CSVReader(reader);
            List<String[]> lines = csvReader.readAll();
            csvReader.close();
            reader.close();
            return lines;
        }
        throw new FileNotFoundException(fileName);
    }

    /**
     * Checks if file exists. If it does, returns file, otherwise returns empty object (used for creating vector of Vehicles).
     * Private method
     * @param fileName name of file to find
     * @return CSV file otherwise empty object
     */
    private Optional<File> openFileWithPathFromEnv(String fileName) {
        String path = System.getenv(fileName);
        if (path != null) {
            return Optional.of(new File(path));
        } else {
            return Optional.empty();
        }
    }

    /**
     * Converts list of String arrays to collection of Vehicles (final step in vector creation).
     * Private method.
     * @param text arguments for building
     * @return Vehicle vector
     */
    private Vector<Vehicle> convertStringListToVehicleVector(List<String[]> text) {
        int corruptedLines = 0;
        int lineCounter = 0;
        Vector<Vehicle> dataSet = new Vector<>();
        for (String[] line : text) {
            ++lineCounter;
            Vehicle vehicle = new Vehicle();
            try {
                if (line.length == 7) {
                    vehicle.getCoordinates()
                            .setX(line[1])
                            .setY(line[2]);
                    vehicle.setName(line[0])
                            .setEnginePower(line[3])
                            .setFuelConsumption(line[4])
                            .setType(line[5])
                            .setFuelType(line[6])
                            .setId(idGenerator.generateRandomID());
                    dataSet.add(vehicle);
                } else {
                    textReceiver.printReport("Line " + lineCounter + " has incorrect number of arguments. Vehicle creation failed");
                    ++corruptedLines;
                }
            } catch (NumberFormatException nfe) {
                textReceiver.printReport("Line " + lineCounter + " has a string value instead of numeric one. Vehicle creation failed");
                ++corruptedLines;
            } catch (NullPointerException npe) {
                textReceiver.printReport("Line " + lineCounter + " has an illegal null value. Vehicle creation failed");
                ++corruptedLines;
            } catch (IllegalArgumentException iae) {
                textReceiver.printReport("Line " + lineCounter + " has an illegal VehicleType/FuelType value. Vehicle creation failed");
                ++corruptedLines;
            } catch (NoArgumentException e) {
                textReceiver.printReport("Line " + lineCounter + " has no argument. Vehicle creation failed");
                ++corruptedLines;
            } catch (LessOrEqualToZeroException e) {
                textReceiver.printReport("Line " + lineCounter + " has an argument that is less or equal to zero. Vehicle creation failed");
                ++corruptedLines;
            }

        }
        textReceiver.printReport("Dataset is ready for use. Number of corrupted lines: " + corruptedLines);
        return dataSet;
    }

    /**
     * Converts Vehicle vector to list of String arrays (used for saving to CSV file)
     * @param dataSet vector of Vehicles
     * @return list of arguments
     */
    private List<String[]> convertVehicleVectorToStringList(Vector<Vehicle> dataSet) {
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
}
