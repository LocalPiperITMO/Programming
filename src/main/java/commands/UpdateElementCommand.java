package commands;

import datatype.Vehicle;
import exceptions.NoArgumentException;

import java.io.IOException;

public class UpdateElementCommand implements Command {
    private final Receiver receiver;

    public UpdateElementCommand(Receiver receiver) {
        this.receiver = receiver;
    }


    public void execute(String argument) throws IOException, NoArgumentException {
        if (checkIfUserInputMatchesRequiredArgument(argument, true)) {
            boolean isFound = false;
            int id = Integer.parseInt(argument);
            for (Vehicle vehicle : receiver.dataSet()) {
                if (id == vehicle.getId()) {
                    isFound = true;
                    receiver.vehicleFactory().switchToConsecutiveInputMode(true);
                    receiver.vehicleFactory().buildOrUpdateVehicleUsingUserInput(vehicle);
                    System.out.println("Object by ID " + id + " updated successfully");
                    break;
                }
            }
            if (!isFound) {
                System.out.println("Object with the given ID does not exist.");
            }
        } else {
            throw new NoArgumentException();
        }
    }
}