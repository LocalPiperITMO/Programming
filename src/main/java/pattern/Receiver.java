package pattern;

import converters.ObjectVectorToStringListConverter;
import datatype.Vehicle;
import exceptions.EmptyDatasetException;
import exceptions.NoPreviousSortingException;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import static app.App.creationDate;
import static converters.CSVtoStringListConverter.varAddress;

public class Receiver {
    Vector<Vehicle> dataSet;
    public static String sortingParameter;

    public Receiver(Vector<Vehicle> dataSet) {
        this.dataSet = dataSet;
    }

    public void show() {
        try {
            if (dataSet.size() == 0) {
                throw new EmptyDatasetException();
            }
            System.out.println("ID Name CreationDate X Y EnginePower FuelConsumption Type FuelType");
            for (Vehicle vehicle : dataSet) {
                System.out.println(vehicle.getId() + " " + vehicle.getName() + " " + vehicle.getCreationDate() + " "
                        + vehicle.getCoordinates() + " " + vehicle.getEnginePower() + " " + vehicle.getFuelConsumption() + " "
                        + vehicle.getType() + " " + vehicle.getFuelType());
            }
        } catch (EmptyDatasetException noData) {
            System.out.println("Dataset is empty: nothing to show");
        }
    }

    public void info() {
        System.out.println("Dataset type: " + dataSet.getClass().getName() + "\nDataset creation date: " + creationDate
                + "\nDataset number of elements: " + this.dataSet.size());
    }

    public void help() {
        System.out.println("""
                Here is the list of supported commands:
                help : displays this message
                info : displays the information about the dataset (type of dataset, creation date, number of elements)
                show : displays every element of the dataset
                exit : leaves the program
                clear : clears the dataset
                reorder : displays every element of the dataset in reverse order of the current sorting.
                If no previous sorting was done, uses default sorting by ID
                print_ascending : displays every element sorted by ID
                print_field_ascending_fuel_type : displays only ID and FuelType of every element, sorted by FuelType
                """);
    }

    public void printAscending() {
        try {
            if (dataSet.size() == 0) {
                throw new EmptyDatasetException();
            }
            sortingParameter = "ID";
            Collections.sort(dataSet);
            show();
        } catch (EmptyDatasetException noData) {
            System.out.println("Dataset is empty: nothing to sort");
        }
    }

    public void printFieldAscendingFuelType() {
        try {
            if (dataSet.size() == 0) {
                throw new EmptyDatasetException();
            }
            sortingParameter = "FuelType";
            Collections.sort(dataSet);
            System.out.println("ID FuelType");
            for (Vehicle vehicle : dataSet) {
                System.out.println(vehicle.getId() + " " + vehicle.getFuelType());
            }
        } catch (EmptyDatasetException noData) {
            System.out.println("Dataset is empty: nothing to sort");
        }
    }

    public void reorder() {
        try {
            if (dataSet.size() == 0) {
                throw new EmptyDatasetException();
            }
            if (sortingParameter == null) {
                throw new NoPreviousSortingException();
            }
            Collections.reverse(dataSet);
            show();

        } catch (NoPreviousSortingException noSort) {
            System.out.println("No previous sorting detected. Switching to default mod: sorting by ID");
            sortingParameter = "ID";
            reorder();
        } catch (EmptyDatasetException noData) {
            System.out.println("Dataset is empty: nothing to sort");
        }
    }

    public void clear() {
        dataSet.removeAllElements();
        System.out.println("Collection has been emptied");
    }

    public void save() throws IOException {
        ObjectVectorToStringListConverter converter = new ObjectVectorToStringListConverter(dataSet);
        List<String> strings = converter.convertObjectVectorToStringList();
        new FileOutputStream(varAddress).close();
        try (FileOutputStream writer = new FileOutputStream(varAddress, true)) {
            for (String stringLine : strings) {
                byte[] buffer = stringLine.getBytes();
                writer.write(buffer, 0, buffer.length);
                writer.write(10);
            }
            System.out.println("File saved successfully.");
        } catch (IOException ioException) {
            System.out.println(ioException.getMessage());
        }
    }

    public void remove_by_id(int id) {
        boolean isFound = false;
        for (Vehicle vehicle : dataSet) {
            isFound = (vehicle.getId() == id);
            if (isFound) {
                dataSet.remove(vehicle);
                System.out.println("Object deleted successfully");
                break;
            }
        }
        if (!isFound) {
            System.out.println("There is no object by this ID.");
        }
    }
}
