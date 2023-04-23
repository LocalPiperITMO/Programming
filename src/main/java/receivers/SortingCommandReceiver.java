package receivers;

import collection.CollectionStorage;
import datatype.Vehicle;
import exceptions.EmptyDatasetException;

import java.util.Collections;

/**
 * Receiver class<br>
 * Stores realization for sorting commands (such as 'print_ascending', 'print_field_ascending_fuel_type' and 'reorder')
 */
public class SortingCommandReceiver {
    /**
     * Stores Vehicle vector
     */
    private final CollectionStorage storage;

    /**
     * Receives the collection
     *
     * @param storage contains the collection
     */
    public SortingCommandReceiver(CollectionStorage storage) {
        this.storage = storage;
    }

    private StringBuilder prepareReportHead() {
        return new StringBuilder(String.format("%7s " + "%-20s " + "%12s " + "%15s " + "%11s " + "%15s " + "%15s " + "%-10s " + "%-10s\n",
                "ID",
                "Name",
                "CreationDate",
                "X",
                "Y",
                "EnginePower",
                "FuelConsumption",
                "Type",
                "FuelType"));
    }

    /**
     * 'print_ascending' command realization
     *
     * @return command execution report (sent to TextReceiver)
     */
    public String printAscending() {
        try {
            if (storage.getDataSet().size() == 0) {
                throw new EmptyDatasetException();
            }

            Collections.sort(storage.getDataSet());
            StringBuilder report = prepareReportHead();
            for (Vehicle vehicle : storage.getDataSet()) {
                report.append(vehicle.toString()).append("\n");
            }
            return String.valueOf(report);
        } catch (EmptyDatasetException noData) {
            return "Dataset is empty: nothing to sort";
        }
    }

    /**
     * 'print_field_ascending_fuel_type' command realization
     *
     * @return command execution report (sent to TextReceiver)
     */
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
            StringBuilder report = new StringBuilder(String.format("%7s " + "%-10s\n", "ID", "FuelType"));
            for (Vehicle vehicle : storage.getDataSet()) {
                report.append(String.format("%7d " + "%-10s\n", vehicle.getId(), vehicle.getFuelType()));
            }
            return String.valueOf(report);
        } catch (EmptyDatasetException noData) {
            return "Dataset is empty: nothing to sort";
        } catch (ArrayIndexOutOfBoundsException notEnoughData) {
            StringBuilder report = new StringBuilder(String.format("%7s " + "%-10s\n", "ID", "FuelType"));
            return String.valueOf(report.append(String.format("%7d " + "%-10s\n", storage.getDataSet().get(0).getId(), storage.getDataSet().get(0).getFuelType())));
        }
    }

    /**
     * 'reorder' command realization
     *
     * @return command execution report (sent to TextReceiver)
     */
    public String reorder() {
        try {
            if (storage.getDataSet().size() == 0) {
                throw new EmptyDatasetException();
            }
            Collections.reverse(storage.getDataSet());
            StringBuilder report = prepareReportHead();
            for (Vehicle vehicle : storage.getDataSet()) {
                report.append(vehicle.toString()).append("\n");
            }
            return String.valueOf(report);
        } catch (EmptyDatasetException noData) {
            return "Dataset is empty: nothing to sort";
        }
    }
}
