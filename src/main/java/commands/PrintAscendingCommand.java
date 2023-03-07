package commands;

import datatype.Vehicle;
import exceptions.EmptyDatasetException;

import java.util.Collections;

public class PrintAscendingCommand implements Command {
    private final Receiver receiver;

    public PrintAscendingCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    public String showInfo() {
        return "No argument required. Displays every element sorted by ID";
    }

    public void execute(String arg) {
        try {
            if (receiver.dataSet().size() == 0) {
                throw new EmptyDatasetException();
            }

            Collections.sort(receiver.dataSet());
            System.out.println("ID Name CreationDate X Y EnginePower FuelConsumption Type FuelType");
            for (Vehicle vehicle : receiver.dataSet()) {
                System.out.println(vehicle.toString());
            }
        } catch (EmptyDatasetException noData) {
            System.out.println("Dataset is empty: nothing to sort");
        }
    }
}
