package commands;

import exceptions.InvalidArgumentsWhileVehicleBuildingViaScriptException;
import receivers.BuilderCommandReceiver;

import java.io.IOException;

public class AddIfMaxElementCommand implements Command {
    private final BuilderCommandReceiver receiver;

    /**
     * "add_if_max" command
     * Creates a new element, asking user for arguments in the process.
     * Created element then compared with the greatest element in the collection (compared using sums of integer arguments, excluding ID)
     * If the created element is greater than the greatest element, it is added to the collection.
     *
     * @param receiver used for storing the collection
     */
    public AddIfMaxElementCommand(BuilderCommandReceiver receiver) {
        this.receiver = receiver;
    }

    /**
     * Used for showing information.
     *
     * @return information about the command
     */
    public String showInfo() {
        return "No argument required. User builds an element. If the element is greater that the greatest element in the dataset, it is added to dataset";
    }

    /**
     * Executes command
     *
     * @param arg command argument
     * @throws IOException                                            if unexpected error occurs
     * @throws InvalidArgumentsWhileVehicleBuildingViaScriptException if invalid arguments given for building vehicle via script
     */
    public String execute(String arg) throws IOException, InvalidArgumentsWhileVehicleBuildingViaScriptException {
        return receiver.addIfMax();
    }
}