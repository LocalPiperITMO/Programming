package commands;

import exceptions.InvalidArgumentsWhileVehicleBuildingViaScriptException;
import exceptions.NoArgumentException;
import receivers.BuilderCommandReceiver;

import java.io.IOException;

public class UpdateElementCommand implements Command {
    private final BuilderCommandReceiver receiver;

    /**
     * "update id" command
     * Checks if the element with the given ID exists
     * Creates a new element, asking user for arguments in the process.
     * Replaces element with the new one
     *
     * @param receiver used for storing the collection
     */
    public UpdateElementCommand(BuilderCommandReceiver receiver) {
        this.receiver = receiver;
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
     * @param argument command argument
     * @throws IOException                                            if unexpected error occurs
     * @throws NoArgumentException                                    if command requires argument, but none were given
     * @throws InvalidArgumentsWhileVehicleBuildingViaScriptException if invalid arguments given for building vehicle via script
     */
    public void execute(String argument) throws IOException, NoArgumentException, InvalidArgumentsWhileVehicleBuildingViaScriptException {
        if (argument.length() != 0) {
            int id = Integer.parseInt(argument);
            receiver.update(id);
        } else {
            throw new NoArgumentException();
        }
    }
}