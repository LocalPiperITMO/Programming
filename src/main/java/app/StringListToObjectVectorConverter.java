package app;

import datatype.Coordinates;
import datatype.FuelType;
import datatype.Vehicle;
import datatype.VehicleType;

import java.util.List;
import java.util.Objects;
import java.util.Vector;

public class StringListToObjectVectorConverter {
    List<String[]> text;
    Vector<Vehicle> dataSet;

    public StringListToObjectVectorConverter(List<String[]> text) {
        this.dataSet = new Vector<>();
        this.text = text;
    }

    public Vector<Vehicle> convertStringListToObjectVector() {
        int corruptedLines = 0;
        String name;
        Coordinates coordinates;
        long enginePower;
        long fuelConsumption;
        VehicleType type;
        FuelType fuelType;
        for (String[] line : this.text) {
            try {
                if (line.length == 7) {
                    name = line[0].strip();
                    coordinates = new Coordinates(Float.parseFloat(line[1]), Integer.parseInt(line[2]));
                    if (Long.parseLong(line[3].strip()) > 0) {
                        enginePower = Long.parseLong(line[3].strip());
                    } else {
                        throw new NumberFormatException();
                    }
                    if (Long.parseLong(line[4].strip()) > 0) {
                        fuelConsumption = Long.parseLong(line[4].strip());
                    } else {
                        throw new NumberFormatException();
                    }
                    line[5] = (Objects.equals(line[5].strip(), "")) ? null : line[5];
                    line[6] = (Objects.equals(line[6].strip(), "")) ? null : line[6];
                    try {
                        type = (line[5] == null) ? null : VehicleType.valueOf(line[5].strip().toUpperCase());
                        fuelType = (line[6] == null) ? null : FuelType.valueOf(line[6].strip().toUpperCase());
                    } catch (IllegalArgumentException iae) {
                        throw new NumberFormatException();
                    }
                    dataSet.add(new Vehicle(name, coordinates, enginePower, fuelConsumption, type, fuelType));
                } else {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException n) {
                ++corruptedLines;
            }
        }
        System.out.println("Dataset is ready for use. Number of corrupted lines: " + corruptedLines);
        return dataSet;
    }
}
