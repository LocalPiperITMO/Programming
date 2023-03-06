package converters;

import datatype.Vehicle;
import exceptions.LessOrEqualToZeroException;
import exceptions.NoArgumentException;
import generators.IDGenerator;

import java.util.List;
import java.util.Vector;

public class StringListToVehicleVectorConverter {
    private final List<String[]> text;
    private final Vector<Vehicle> dataSet;
    private final IDGenerator idGenerator = new IDGenerator();

    public StringListToVehicleVectorConverter(List<String[]> text) {
        this.dataSet = new Vector<>();
        this.text = text;


    }

    public Vector<Vehicle> convertStringListToObjectVector() {
        int corruptedLines = 0;
        int lineCounter = 0;
        for (String[] line : this.text) {
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

    public IDGenerator getIdGenerator() {
        return idGenerator;
    }
}