package collection_handling;

import data.Vehicle;

import java.util.List;
import java.util.Vector;

public class CollectionManager {
    public final static Vector<Vehicle> dataset = new Vector<>();

    public void initCollection(List<Vehicle> vehicleList) {
        for (Vehicle vehicle : vehicleList) {
            vehicle.setId(CollectionIDGenerator.generateID());
            dataset.add(vehicle);
        }
    }
}
