package commands;

import datatype.Vehicle;
import exceptions.InvalidArgumentsWhileVehicleBuildingViaScriptException;

import java.io.IOException;

public class AddElementCommand implements Command {
    private final Receiver receiver;
    private final Invoker invoker;

    /**
     * "add" command
     * Creates a new element, asking user for arguments in the process. Created element then added to the collection
     *
     * @param receiver used for storing the collection
     * @param invoker  used for storing arguments for building (used only via "execute_script" command)
     */

    public AddElementCommand(Receiver receiver, Invoker invoker) {
        this.receiver = receiver;
        this.invoker = invoker;
    }

    /**
     * Used for showing information.
     *
     * @return information about the command
     */
    public String showInfo() {
        return "No argument required. User builds an element. Adds element to dataset";
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
        vehicle.setId(receiver.idGenerator().generateRandomID());
        receiver.dataSet().add(vehicle);
        System.out.println("Vehicle added successfully!");
    }
}
