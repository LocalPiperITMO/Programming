package commands;

import datatype.Vehicle;
import exceptions.NoArgumentException;

import java.io.IOException;

public class RemoveByIDCommand implements Command {
    private final Receiver receiver;

    public RemoveByIDCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    public String showInfo() {
        return "Required argument - ID(numeric). Removes an element with the given ID";
    }

    public void execute(String argument, boolean isCalledByScript) throws IOException, NoArgumentException {
        if (checkIfUserInputMatchesRequiredArgument(argument, true)) {
            int id = Integer.parseInt(argument);
            boolean isFound = false;
            for (Vehicle vehicle : receiver.dataSet()) {
                isFound = (vehicle.getId() == id);
                if (isFound) {
                    receiver.dataSet().remove(vehicle);
                    System.out.println("Object deleted successfully");
                    break;
                }
            }
            if (!isFound) {
                System.out.println("There is no object by this ID.");
            }
        } else {
            throw new NoArgumentException();
        }
    }
}
