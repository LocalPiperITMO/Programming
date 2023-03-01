package converters;

import datatype.Vehicle;
import generators.VehicleBuilder;

import java.util.List;
import java.util.Vector;

public class StringListToObjectVectorConverter {
    private final List<String[]> text;
    private final Vector<Vehicle> dataSet;
    private final VehicleBuilder vehicleBuilder = new VehicleBuilder();

    public StringListToObjectVectorConverter(List<String[]> text) {
        this.dataSet = new Vector<>();
        this.text = text;


    }

    public VehicleBuilder getVehicleFactory() {
        return vehicleBuilder;
    }

    public Vector<Vehicle> convertStringListToObjectVector() {
        int corruptedLines = 0;
        int lineCounter = 0;
        vehicleBuilder.switchToConsecutiveInputMode(false);
        for (String[] line : this.text) {
            ++lineCounter;
            Vehicle vehicle;
            vehicle = vehicleBuilder.buildVehicleUsingPredefinedData(line, lineCounter);
            if (vehicle == null) {
                ++corruptedLines;
            } else {
                dataSet.add(vehicle);
            }
        }
        System.out.println("Dataset is ready for use. Number of corrupted lines: " + corruptedLines);
        return dataSet;
    }
}
