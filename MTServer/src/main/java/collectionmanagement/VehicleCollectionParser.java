package collectionmanagement;

import datatypes.Vehicle;
import exceptions.LessOrEqualToZeroException;
import exceptions.NoArgumentException;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class VehicleCollectionParser extends AbstractCollectionParser<Vehicle> {
    private int lineCounter = 0;
    private int corruptedLines = 0;

    private Optional<Vehicle> buildVehicleIfPossible(String[] parameters) {
        try {
            if (parameters.length == 10) {
                Vehicle vehicle = new Vehicle();

                vehicle.setName(parameters[1])
                        .setCreationDate(LocalDate.parse(parameters[4]))
                        .getCoordinates()
                        .setX(parameters[2])
                        .setY(parameters[3]);
                vehicle.setEnginePower(parameters[5])
                        .setFuelConsumption(parameters[6])
                        .setType(Objects.equals(parameters[7], "null") ? "" : parameters[7])
                        .setFuelType(Objects.equals(parameters[8], "null") ? "" : parameters[8]);
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

    @Override
    ConcurrentHashMap<Integer, Vehicle> convertMapData(List<String[]> dataList) {
        ConcurrentHashMap<Integer, Vehicle> resultingMap = new ConcurrentHashMap<>();
        receiver.printToLog(this.getClass().getSimpleName(), "Parsing data...");
        for (String[] line : dataList) {
            ++lineCounter;
            Optional<Vehicle> vehicle = buildVehicleIfPossible(line);
            if (vehicle.isPresent()) {
                resultingMap.put(Integer.valueOf(line[0]), vehicle.get());
            } else {
                receiver.printToLog(this.getClass().getSimpleName(), "Vehicle creation failed");
            }
        }
        if (dataList.size() == 0) {
            return new ConcurrentHashMap<>();
        }
        receiver.printToLog(this.getClass().getSimpleName(), "Collection is ready for use. Number of corrupted lines: " + corruptedLines);
        return resultingMap;
    }
}
