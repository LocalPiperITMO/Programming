package commands;

import datatype.Vehicle;

import java.io.IOException;

public class AddElementCommand implements Command {
    private final Receiver receiver;

    public AddElementCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    public String showInfo() {
        return "No argument required. User builds an element. Adds element to dataset";
    }

    public void execute(String arg) throws IOException {
        Vehicle vehicle = buildVehicleViaUserInput((new Vehicle()).setId(receiver.idGenerator().generateRandomID()));
        receiver.dataSet().add(vehicle);
        System.out.println("Vehicle added successfully!");
    }
}
