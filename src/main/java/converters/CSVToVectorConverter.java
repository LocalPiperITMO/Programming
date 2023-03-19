package converters;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import datatype.Vehicle;
import exceptions.LessOrEqualToZeroException;
import exceptions.NoArgumentException;
import generators.IDGenerator;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Vector;

public class CSVToVectorConverter {
    private final String fileName;
    private Path varPath;
    private final IDGenerator idGenerator = new IDGenerator();

    public CSVToVectorConverter(String fileName) {
        this.fileName = fileName;
    }

    public Path getVarPath() {
        return varPath;
    }

    public IDGenerator getIdGenerator() {
        return idGenerator;
    }

    public Vector<Vehicle> getVector() throws IOException, CsvException {
        return convertStringListToVehicleVector(convertCSVtoStringList(this.fileName));
    }

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

    private Optional<File> openFileWithPathFromEnv(String fileName) {
        String path = System.getenv(fileName);
        if (path != null) {
            varPath = Path.of(path);
            return Optional.of(new File(path));
        } else {
            return Optional.empty();
        }
    }

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
                    System.out.println("Line " + lineCounter + " has incorrect number of arguments. Vehicle creation failed");
                    ++corruptedLines;
                }
            } catch (NumberFormatException nfe) {
                System.out.println("Line " + lineCounter + " has a string value instead of numeric one. Vehicle creation failed");
                ++corruptedLines;
            } catch (NullPointerException npe) {
                System.out.println("Line " + lineCounter + " has an illegal null value. Vehicle creation failed");
                ++corruptedLines;
            } catch (IllegalArgumentException iae) {
                System.out.println("Line " + lineCounter + " has an illegal VehicleType/FuelType value. Vehicle creation failed");
                ++corruptedLines;
            } catch (NoArgumentException e) {
                System.out.println("Line " + lineCounter + " has no argument. Vehicle creation failed");
                ++corruptedLines;
            } catch (LessOrEqualToZeroException e) {
                System.out.println("Line " + lineCounter + " has an argument that is less or equal to zero. Vehicle creation failed");
                ++corruptedLines;
            }

        }
        System.out.println("Dataset is ready for use. Number of corrupted lines: " + corruptedLines);
        return dataSet;
    }

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
