package commands;

import exceptions.InvalidArgumentsWhileVehicleBuildingViaScriptException;
import receivers.BuilderCommandReceiver;

import java.io.IOException;
/**
 * Command for removing elements from the collection if the condition (greater than the current) is matched.
 * Has 2 different realizations: by user input and by script arguments
 */
public class RemoveGreaterElementsCommand implements Command {
    /**
     * Receiver that contains required method for the command
     */
    private final BuilderCommandReceiver receiver;

    /**
     * @param receiver receiver with command realization
     */
    public RemoveGreaterElementsCommand(BuilderCommandReceiver receiver) {
        this.receiver = receiver;
    }

    /**
     * @return information about the command
     */
    public String showInfo() {
        return "No argument required. User builds an element. Removes all the elements from the dataset greater than the given one";
    }

    /**
     * Calls method from the receiver
     *
     * @param arg command argument
     * @return report on command execution
     * @throws InvalidArgumentsWhileVehicleBuildingViaScriptException if script building went wrong
     */
    public String execute(String arg) throws IOException, InvalidArgumentsWhileVehicleBuildingViaScriptException {
        return receiver.removeGreater();
    }
}