package commands;

import exceptions.InvalidArgumentsWhileVehicleBuildingViaScriptException;
import receivers.BuilderCommandReceiver;

import java.io.IOException;

public class RemoveGreaterElementsCommand implements Command {
    private final BuilderCommandReceiver receiver;

    /**
     * "remove_greater" command
     * Creates a new element, asking user for arguments in the process.
     * Removes all elements lesser than the given one from the collection
     * Elements are compared via sums of their integer arguments
     *
     * @param receiver used for storing the collection
     */
    public RemoveGreaterElementsCommand(BuilderCommandReceiver receiver) {
        this.receiver = receiver;
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
     * @param arg command argument
     * @throws IOException                                            if unexpected error occurs
     * @throws InvalidArgumentsWhileVehicleBuildingViaScriptException if invalid arguments given for building vehicle via script
     */
    public void execute(String arg) throws IOException, InvalidArgumentsWhileVehicleBuildingViaScriptException {
        receiver.removeGreater();
    }
}