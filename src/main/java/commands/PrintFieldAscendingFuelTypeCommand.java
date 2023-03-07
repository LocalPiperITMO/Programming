package commands;

import datatype.Vehicle;
import exceptions.EmptyDatasetException;

import java.util.Collections;

public class PrintFieldAscendingFuelTypeCommand implements Command {
    private final Receiver receiver;

    public PrintFieldAscendingFuelTypeCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    public String showInfo() {
        return "No argument required. Displays only ID and FuelType of every element, sorted by FuelType";
    }

    public void execute(String arg) {
        try {
            if (receiver.dataSet().size() == 0) {
                throw new EmptyDatasetException();
            }
            int index = 0;
            do {
                int current = (receiver.dataSet().get(index).getFuelType() == null) ? 0 : receiver.dataSet().get(index).getFuelType().getPosition();
                int next = (receiver.dataSet().get(index + 1).getFuelType() == null) ? 0 : receiver.dataSet().get(index + 1).getFuelType().getPosition();
                if (current > next) {
                    Collections.swap(receiver.dataSet(), index, index + 1);
                    index = 0;
                } else {
                    ++index;
                }
            } while (index != receiver.dataSet().size() - 1);
            System.out.println("ID FuelType");
            for (Vehicle vehicle : receiver.dataSet()) {
                System.out.println(vehicle.getId() + " " + vehicle.getFuelType());
            }
        } catch (EmptyDatasetException noData) {
            System.out.println("Dataset is empty: nothing to sort");
        }
    }
}
