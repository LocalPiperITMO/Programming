package pattern;

import datatype.Vehicle;

import java.util.Collections;
import java.util.Vector;

import static app.App.creationDate;

public class Receiver {
    Vector<Vehicle> dataSet;

    public Receiver(Vector<Vehicle> dataSet) {
        this.dataSet = dataSet;
    }

    public void show() {
        Collections.sort(dataSet);
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
                """);
    }
}
