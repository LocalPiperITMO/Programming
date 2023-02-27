package pattern;

import datatype.Vehicle;
import exceptions.NoPreviousSortingException;

import java.util.Collections;
import java.util.Vector;

import static app.App.creationDate;

public class Receiver {
    Vector<Vehicle> dataSet;
    public static String sortingParameter;

    public Receiver(Vector<Vehicle> dataSet) {
        this.dataSet = dataSet;
    }

    public void show() {
        System.out.println("ID Name CreationDate X Y EnginePower FuelConsumption Type FuelType");
        for (Vehicle vehicle : dataSet) {
            System.out.println(vehicle.getId() + " " + vehicle.getName() + " " + vehicle.getCreationDate() + " "
                    + vehicle.getCoordinates() + " " + vehicle.getEnginePower() + " " + vehicle.getFuelConsumption() + " "
                    + vehicle.getType() + " " + vehicle.getFuelType());
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
                reorder : displays every element of the dataset in reverse order of the current sorting.
                If no previous sorting was done, uses default sorting by ID
                print_ascending : displays every element sorted by ID
                print_field_ascending_fuel_type : displays only ID and FuelType of every element, sorted by FuelType
                """);
    }

    public void printAscending() {
        sortingParameter = "ID";
        Collections.sort(dataSet);
        show();
    }

    public void printFieldAscendingFuelType() {
        sortingParameter = "FuelType";
        Collections.sort(dataSet);
        System.out.println("ID FuelType");
        for (Vehicle vehicle : dataSet) {
            System.out.println(vehicle.getId() + " " + vehicle.getFuelType());
        }
    }

    public void reorder() {
        try {
            if (sortingParameter == null) {
                throw new NoPreviousSortingException();
            }
            Collections.reverse(dataSet);
            show();

        } catch (NoPreviousSortingException noSort) {
            System.out.println("No previous sorting detected. Switching to default mod: sorting by ID");
            sortingParameter = "ID";
            reorder();
        }
    }
}
