package commands;

import datatype.Vehicle;

import java.io.IOException;

public class RemoveGreaterElementsCommand implements Command {
    private final Receiver receiver;

    public RemoveGreaterElementsCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    public void execute(String arg) throws IOException {
        receiver.vehicleFactory().switchToConsecutiveInputMode(true);
        Vehicle elementToCompare = receiver.vehicleFactory().buildOrUpdateVehicleUsingUserInput(null);
        receiver.dataSet().removeIf(vehicle -> vehicle.getSum() > elementToCompare.getSum());

        System.out.println("Done");
    }
}