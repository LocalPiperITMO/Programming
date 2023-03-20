package commands;

import exceptions.InvalidArgumentsWhileVehicleBuildingViaScriptException;
import receivers.BuilderCommandReceiver;

import java.io.IOException;

public class AddElementCommand implements Command {
    private final BuilderCommandReceiver receiver;

    /**
     * "add" command
     * Creates a new element, asking user for arguments in the process. Created element then added to the collection
     */

    public AddElementCommand(BuilderCommandReceiver receiver) {
        this.receiver = receiver;
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
     * @param arg command argument
     * @throws IOException                                            if unexpected error occurs
     * @throws InvalidArgumentsWhileVehicleBuildingViaScriptException if invalid arguments given for building vehicle via script
     */
    public void execute(String arg) throws IOException, InvalidArgumentsWhileVehicleBuildingViaScriptException {
        receiver.addVehicle();
    }
}
