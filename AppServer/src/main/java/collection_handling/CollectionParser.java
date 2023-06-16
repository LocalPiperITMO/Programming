package collection_handling;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import data.Vehicle;
import exceptions.LessOrEqualToZeroException;
import exceptions.NoArgumentException;
import receivers.TextReceiver;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static collection_handling.CollectionManager.dataset;

public class CollectionParser {
    private int lineCounter = 0;
    private int corruptedLines = 0;

    private final TextReceiver receiver = new TextReceiver();

    public List<Vehicle> parseToVector(File file) {
        List<String[]> dataList = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(file))) {
            dataList = reader.readAll();
        } catch (IOException | CsvException e) {
            File emptyFile = new File("empty.csv");
            receiver.printToLog(this.getClass().getSimpleName(), "Reading failed. Opening new file...");
            parseToVector(emptyFile);
        }
        return createListAndFillWithData(dataList);

    }


    public List<String[]> parseToStringList() {
        //Opel,124,4,51,21,CHOPPER,MANPOWER
        List<String[]> stringList = new ArrayList<>();
        for (Vehicle vehicle : dataset) {
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

    private List<Vehicle> createListAndFillWithData(List<String[]> buildingMaterial) {
        List<Vehicle> resultingList = new ArrayList<>();
        receiver.printToLog(this.getClass().getSimpleName(), "Parsing data...");
        for (String[] line : buildingMaterial) {
            ++lineCounter;
            Optional<Vehicle> vehicle = buildVehicleIfPossible(line);
            if (vehicle.isPresent()) {
                resultingList.add(vehicle.get());
            } else {
                receiver.printToLog(this.getClass().getSimpleName(), "Vehicle creation failed");
            }
        }
        if (buildingMaterial.size() == 0) {
            return new ArrayList<>();
        }
        receiver.printToLog(this.getClass().getSimpleName(), "Collection is ready for use. Number of corrupted lines: " + corruptedLines);
        return resultingList;

    }

    private Optional<Vehicle> buildVehicleIfPossible(String[] parameters) {
        try {
            if (parameters.length == 7) {
                Vehicle vehicle = new Vehicle();
                vehicle.setName(parameters[0])
                        .getCoordinates()
                        .setX(parameters[1])
                        .setY(parameters[2]);
                vehicle.setEnginePower(parameters[3])
                        .setFuelConsumption(parameters[4])
                        .setType(parameters[5])
                        .setFuelType(parameters[6]);
                return Optional.of(vehicle);
            } else {
                receiver.printToLog(this.getClass().getSimpleName(), "Line " + lineCounter + " has incorrect number of arguments");
                ++corruptedLines;
            }
        } catch (NoArgumentException e) {
            receiver.printToLog(this.getClass().getSimpleName(), "Line " + lineCounter + " has no argument");
            ++corruptedLines;
        } catch (LessOrEqualToZeroException e) {
            receiver.printToLog(this.getClass().getSimpleName(), "Line " + lineCounter + " has an argument that is less or equal to zero");
            ++corruptedLines;
        } catch (NumberFormatException e) {
            receiver.printToLog(this.getClass().getSimpleName(), "Line " + lineCounter + " has a string value instead of numeric one");
            ++corruptedLines;
        } catch (IllegalArgumentException e) {
            receiver.printToLog(this.getClass().getSimpleName(), "Line " + lineCounter + " has an illegal VehicleType/FuelType value.");
            ++corruptedLines;
        }
        return Optional.empty();
    }

}
