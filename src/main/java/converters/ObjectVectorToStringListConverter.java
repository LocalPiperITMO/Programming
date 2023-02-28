package converters;

import datatype.Vehicle;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class ObjectVectorToStringListConverter {
    Vector<Vehicle> dataSet;
    List<String> stringList;

    public ObjectVectorToStringListConverter(Vector<Vehicle> dataSet) {
        this.dataSet = dataSet;
    }

    public List<String> convertObjectVectorToStringList() {
        //Opel,124,4,51,21,CHOPPER,MANPOWER
        stringList = new ArrayList<>();
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
