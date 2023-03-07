package commands;

import datatype.Vehicle;

import java.io.IOException;

public class RemoveGreaterElementsCommand implements Command {
    private final Receiver receiver;

    public RemoveGreaterElementsCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    public void execute(String arg) throws IOException {
        Vehicle vehicle = buildVehicleViaUserInput((new Vehicle()).setId(receiver.idGenerator().generateRandomID()));
        receiver.dataSet().removeIf(vehicleToCompare -> vehicleToCompare.getSum() > vehicle.getSum());
        System.out.println("Done");
    }
}