package commands;

import exceptions.InvalidArgumentsWhileVehicleBuildingViaScriptException;
import receivers.BuilderCommandReceiver;

import java.io.IOException;

/**
 * Command for adding elements to the collection.
 * Has 2 different realizations: by user input and by script arguments
 */
public class AddElementCommand implements Command {
    /**
     * Receiver that contains required method for the command
     */
    private final BuilderCommandReceiver receiver;

    /**
     * @param receiver receiver with command realization
     */
    public AddElementCommand(BuilderCommandReceiver receiver) {
        this.receiver = receiver;
    }

    /**
     * @return information about the command
     */
    public String showInfo() {
        return "No argument required. User builds an element. Adds element to dataset";
    }

    /**
     * Calls method from the receiver
     * @param arg command argument
     * @return report on command execution
     * @throws InvalidArgumentsWhileVehicleBuildingViaScriptException if script building went wrong
     */
    public String execute(String arg) throws IOException, InvalidArgumentsWhileVehicleBuildingViaScriptException {
        return receiver.addVehicle();
    }
}
