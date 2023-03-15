package commands;

import datatype.Vehicle;
import exceptions.InvalidArgumentsWhileVehicleBuildingViaScriptException;
import exceptions.NoArgumentException;

import java.io.IOException;

public class UpdateElementCommand implements Command {
    private final Receiver receiver;
    private final Invoker invoker;

    /**
     * "update id" command
     * Checks if the element with the given ID exists
     * Creates a new element, asking user for arguments in the process.
     * Replaces element with the new one
     *
     * @param receiver used for storing the collection
     * @param invoker  used for storing arguments for building (used only via "execute_script" command)
     */
    public UpdateElementCommand(Receiver receiver, Invoker invoker) {
        this.receiver = receiver;
        this.invoker = invoker;
    }

    /**
     * Used for showing information.
     *
     * @return information about the command
     */
    public String showInfo() {
        return "Required argument - ID(numeric). User builds an element. Updates the element with the given ID";
    }

    /**
     * Executes command
     *
     * @param argument         command argument
     * @param isCalledByScript checks if command called from script
     * @throws IOException                                            if unexpected error occurs
     * @throws NoArgumentException                                    if command requires argument, but none were given
     * @throws InvalidArgumentsWhileVehicleBuildingViaScriptException if invalid arguments given for building vehicle via script
     */
    public void execute(String argument, boolean isCalledByScript) throws IOException, NoArgumentException, InvalidArgumentsWhileVehicleBuildingViaScriptException {
        if (checkIfUserInputMatchesRequiredArgument(argument, true)) {
            try {
                int id = Integer.parseInt(argument);
                boolean isFound = false;
                Vehicle vehicle = new Vehicle();
                for (Vehicle vehicleToFind : receiver.dataSet()) {
                    if (vehicleToFind.getId() == id) {
                        vehicle = vehicleToFind;
                        isFound = true;
                        break;
                    }
                }
                if (!isFound) {
                    System.out.println("Element with given ID does not exist");
                } else {
                    if (isCalledByScript) {
                        vehicle = buildVehicleViaScript(invoker.getListOfArgumentsForBuildingViaScript());
                    } else {
                        vehicle = buildVehicleViaUserInput(vehicle);
                    }
                    System.out.println("Vehicle by ID " + vehicle.getId() + " updated successfully");

                }
            } catch (NumberFormatException e) {
                System.out.println("ID must be integer, not string");
            }
        } else {
            throw new NoArgumentException();
        }
    }
}