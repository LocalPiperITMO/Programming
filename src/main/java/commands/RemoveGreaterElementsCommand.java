package commands;

import datatype.Vehicle;

import java.io.IOException;

public class RemoveGreaterElementsCommand implements Command {
    private final Receiver receiver;

    public RemoveGreaterElementsCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    public String showInfo() {
        return "No argument required. User builds an element. Removes all the elements from the dataset greater than the given one";
    }

    public void execute(String arg) throws IOException {
        Vehicle vehicle = buildVehicleViaUserInput((new Vehicle()).setId(receiver.idGenerator().generateRandomID()));
        receiver.dataSet().removeIf(vehicleToCompare -> vehicleToCompare.getSum() > vehicle.getSum());
        System.out.println("Done");
    }
}