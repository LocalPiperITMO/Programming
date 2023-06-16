package collection_handling;

import data.Vehicle;

import java.util.HashSet;
import java.util.Set;

import static collection_handling.CollectionManager.dataset;

public class CollectionIDGenerator {

    public static int generateID() {
        Set<Integer> IDSet = new HashSet<>();
        for (Vehicle vehicle : dataset) {
            if (vehicle.getId() != null) {
                IDSet.add(vehicle.getId());
            }
        }
        int min = 1;
        while (IDSet.contains(min)) {
            min++;
        }
        return min;
    }
}
