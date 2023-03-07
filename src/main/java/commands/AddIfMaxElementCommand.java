package commands;

import datatype.Vehicle;

import java.io.IOException;
import java.util.Collections;

public class AddIfMaxElementCommand implements Command {
    private final Receiver receiver;

    public AddIfMaxElementCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    public void execute(String arg) throws IOException {
        Vehicle vehicle = buildVehicleViaUserInput((new Vehicle()).setId(receiver.idGenerator().generateRandomID()));
        int index = 0;
        do {
            if (receiver.dataSet().get(index).getSum() > receiver.dataSet().get(index + 1).getSum()) {
                Collections.swap(receiver.dataSet(), index, index + 1);
                index = 0;
            } else {
                ++index;
            }
        } while (index != receiver.dataSet().size() - 1);
        if (vehicle.compareTo(receiver.dataSet().lastElement()) > 0) {
            receiver.dataSet().add(vehicle);
            System.out.println("New element added successfully");
        } else {
            System.out.println("New element has not been added: element with ID " + receiver.dataSet().lastElement().getId() + " is greater");
        }
    }
}