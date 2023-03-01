package commands;

import pattern.Command;
import pattern.Receiver;

import java.io.IOException;

public class AddElementCommand implements Command {
    private final Receiver receiver;

    public AddElementCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    public void execute(String arg) throws IOException {
        receiver.vehicleFactory().switchToConsecutiveInputMode(true);
        receiver.dataSet().add(receiver.vehicleFactory().createOrUpdateObjectByUserInput(null));
        System.out.println("New element added successfully");
    }
}
