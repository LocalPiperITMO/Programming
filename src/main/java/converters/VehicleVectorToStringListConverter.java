package converters;

import datatype.Vehicle;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class VehicleVectorToStringListConverter {
    private Vector<Vehicle> dataSet;

    /**
     * Converter class
     * Converts vector of elements back into list of arguments (used by "save" command)
     *
     * @param dataSet collection
     */
    public VehicleVectorToStringListConverter(Vector<Vehicle> dataSet) {
        this.dataSet = dataSet;
    }

    /**
     * USed for settinhg the collection
     *
     * @param vector collection
     */
    public void setDataSet(Vector<Vehicle> vector) {
        this.dataSet = vector;
    }

    /**
     * Used for converting collection to list of arguments
     *
     * @return list of arguments
     */
    public List<String> convertVehicleVectorToStringList() {
        //Opel,124,4,51,21,CHOPPER,MANPOWER
        List<String> stringList = new ArrayList<>();
        for (Vehicle vehicle : dataSet) {
            String type = (vehicle.getType() == null) ? "" : String.valueOf(vehicle.getType());
            String fuelType = (vehicle.getFuelType() == null) ? "" : String.valueOf(vehicle.getFuelType());
            String line = vehicle.getName() + "," + vehicle.getCoordinates().getX() + ","
                    + vehicle.getCoordinates().getY() + "," + vehicle.getEnginePower() + "," +
                    vehicle.getFuelConsumption() + "," + type + "," + fuelType;
            stringList.add(line);
        }

        return stringList;
    }
}
