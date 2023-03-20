package receivers;

import collection.CollectionStorage;
import datatype.Vehicle;
import exceptions.EmptyDatasetException;

import java.util.Collections;

public class SortingCommandReceiver {
    private final CollectionStorage storage;

    public SortingCommandReceiver(CollectionStorage storage) {
        this.storage = storage;
    }

    public void printAscending() {
        try {
            if (storage.getDataSet().size() == 0) {
                throw new EmptyDatasetException();
            }

            Collections.sort(storage.getDataSet());
            System.out.println("ID Name CreationDate X Y EnginePower FuelConsumption Type FuelType");
            for (Vehicle vehicle : storage.getDataSet()) {
                System.out.println(vehicle.toString());
            }
        } catch (EmptyDatasetException noData) {
            System.out.println("Dataset is empty: nothing to sort");
        }
    }

    public void printFieldAscendingFuelType() {
        try {
            if (storage.getDataSet().size() == 0) {
                throw new EmptyDatasetException();
            }
            int index = 0;
            do {
                int current = (storage.getDataSet().get(index).getFuelType() == null) ? 0 : storage.getDataSet().get(index).getFuelType().getPosition();
                int next = (storage.getDataSet().get(index + 1).getFuelType() == null) ? 0 : storage.getDataSet().get(index + 1).getFuelType().getPosition();
                if (current > next) {
                    Collections.swap(storage.getDataSet(), index, index + 1);
                    index = 0;
                } else {
                    ++index;
                }
            } while (index != storage.getDataSet().size() - 1);
            System.out.println("ID FuelType");
            for (Vehicle vehicle : storage.getDataSet()) {
                System.out.println(vehicle.getId() + " " + vehicle.getFuelType());
            }
        } catch (
                EmptyDatasetException noData) {
            System.out.println("Dataset is empty: nothing to sort");
        }
    }

    public void reorder() {
        try {
            if (storage.getDataSet().size() == 0) {
                throw new EmptyDatasetException();
            }
            Collections.reverse(storage.getDataSet());
            System.out.println("ID Name CreationDate X Y EnginePower FuelConsumption Type FuelType");
            for (Vehicle vehicle : storage.getDataSet()) {
                System.out.println(vehicle.toString());
            }
        } catch (EmptyDatasetException noData) {
            System.out.println("Dataset is empty: nothing to sort");
        }
    }
}
