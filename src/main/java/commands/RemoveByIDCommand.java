package commands;

import datatype.Vehicle;
import exceptions.NoArgumentException;

import java.io.IOException;

public class RemoveByIDCommand implements Command {
    private final Receiver receiver;

    /**
     * "remove_by_id id" command
     * Removes element with given ID from the collection, if one exists
     *
     * @param receiver used for storing the collection
     */
    public RemoveByIDCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    /**
     * Used for showing information.
     *
     * @return information about the command
     */
    public String showInfo() {
        return "Required argument - ID(numeric). Removes an element with the given ID";
    }

    /**
     * Executes command
     *
     * @param argument         command argument
     * @param isCalledByScript checks if command called from script
     * @throws IOException         if unexpected error occurs
     * @throws NoArgumentException if command requires argument, but none were given
     */
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
