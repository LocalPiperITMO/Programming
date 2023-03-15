package commands;

import datatype.Vehicle;
import exceptions.EmptyDatasetException;

import java.util.Collections;


public class ReorderCommand implements Command {
    private final Receiver receiver;

    /**
     * "reorder" command
     * Reverses the element order of the collection
     *
     * @param receiver used for storing the collection
     */
    public ReorderCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    /**
     * Used for showing information.
     *
     * @return information about the command
     */
    public String showInfo() {
        return "No argument required. Displays every element of the dataset in reverse order of the current sorting";
    }

    /**
     * Executes command
     *
     * @param arg              command argument
     * @param isCalledByScript checks if command called from script
     */
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
