package commands;

import datatype.Vehicle;
import exceptions.InvalidArgumentsWhileVehicleBuildingViaScriptException;

import java.io.IOException;

public class RemoveGreaterElementsCommand implements Command {
    private final Receiver receiver;
    private final Invoker invoker;

    /**
     * "remove_greater" command
     * Creates a new element, asking user for arguments in the process.
     * Removes all elements lesser than the given one from the collection
     * Elements are compared via sums of their integer arguments
     *
     * @param receiver used for storing the collection
     * @param invoker  used for storing arguments for building (used only via "execute_script" command)
     */
    public RemoveGreaterElementsCommand(Receiver receiver, Invoker invoker) {
        this.receiver = receiver;
        this.invoker = invoker;
    }

    /**
     * Used for showing information.
     *
     * @return information about the command
     */
    public String showInfo() {
        return "No argument required. User builds an element. Removes all the elements from the dataset greater than the given one";
    }

    /**
     * Executes command
     *
     * @param arg              command argument
     * @param isCalledByScript checks if command called from script
     * @throws IOException                                            if unexpected error occurs
     * @throws InvalidArgumentsWhileVehicleBuildingViaScriptException if invalid arguments given for building vehicle via script
     */
    public void execute(String arg, boolean isCalledByScript) throws IOException, InvalidArgumentsWhileVehicleBuildingViaScriptException {
        Vehicle vehicle;
        if (isCalledByScript) {
            vehicle = buildVehicleViaScript(invoker.getListOfArgumentsForBuildingViaScript());
        } else {
            vehicle = buildVehicleViaUserInput((new Vehicle()));
        }
        receiver.dataSet().removeIf(vehicleToCompare -> vehicleToCompare.getSum() > vehicle.getSum());
        System.out.println("Done");
    }
}