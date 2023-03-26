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

    public String printAscending() {
        try {
            if (storage.getDataSet().size() == 0) {
                throw new EmptyDatasetException();
            }

            Collections.sort(storage.getDataSet());
            StringBuilder report = new StringBuilder("ID Name CreationDate X Y EnginePower FuelConsumption Type FuelType");
            for (Vehicle vehicle : storage.getDataSet()) {
                report.append(vehicle.toString()).append("\n");
            }
            return String.valueOf(report);
        } catch (EmptyDatasetException noData) {
            return "Dataset is empty: nothing to sort";
        }
    }

    public String printFieldAscendingFuelType() {
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
            StringBuilder report = new StringBuilder("ID FuelType\n");
            for (Vehicle vehicle : storage.getDataSet()) {
                report.append(vehicle.getId()).append(" ").append(vehicle.getFuelType()).append("\n");
            }
            return String.valueOf(report);
        } catch (EmptyDatasetException noData) {
            return "Dataset is empty: nothing to sort";
        }
    }

    public String reorder() {
        try {
            if (storage.getDataSet().size() == 0) {
                throw new EmptyDatasetException();
            }
            Collections.reverse(storage.getDataSet());
            StringBuilder report = new StringBuilder("ID Name CreationDate X Y EnginePower FuelConsumption Type FuelType\n");
            for (Vehicle vehicle : storage.getDataSet()) {
                report.append(vehicle.toString()).append("\n");
            }
            return String.valueOf(report);
        } catch (EmptyDatasetException noData) {
            return "Dataset is empty: nothing to sort";
        }
    }
}
