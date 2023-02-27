package pattern;

import datatype.Vehicle;

import java.util.Vector;

public class Receiver {
    Vector<Vehicle> dataSet;

    public Receiver(Vector<Vehicle> dataSet) {
        this.dataSet = dataSet;
    }

    public void show() {
        for (Vehicle vehicle : dataSet) {
            System.out.println(vehicle.getId() + " " + vehicle.getName() + " " + vehicle.getCreationDate() + " "
                    + vehicle.getCoordinates() + " " + vehicle.getEnginePower() + " " + vehicle.getFuelConsumption() + " "
                    + vehicle.getType() + " " + vehicle.getFuelType());
        }
    }
}
