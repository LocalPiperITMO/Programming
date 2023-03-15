package commands;

import datatype.Vehicle;
import exceptions.EmptyDatasetException;

import java.util.Collections;


public class ReorderCommand implements Command {
    private final Receiver receiver;

    public ReorderCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    public String showInfo() {
        return "No argument required. Displays every element of the dataset in reverse order of the current sorting";
    }

    public void execute(String arg, boolean isCalledByScript) {
        try {
            if (receiver.dataSet().size() == 0) {
                throw new EmptyDatasetException();
            }
            Collections.reverse(receiver.dataSet());
            System.out.println("ID Name CreationDate X Y EnginePower FuelConsumption Type FuelType");
            for (Vehicle vehicle : receiver.dataSet()) {
                System.out.println(vehicle.toString());
            }
        } catch (EmptyDatasetException noData) {
            System.out.println("Dataset is empty: nothing to sort");
        }
    }
}
