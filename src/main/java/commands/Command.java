package commands;

import exceptions.InvalidArgumentsWhileVehicleBuildingViaScriptException;
import exceptions.NoArgumentException;

import java.io.IOException;

public interface Command {
    /**
     * Used for showing information.
     *
     * @return information about the command
     */
    String showInfo();

    /**
     * Executes command
     *
     * @param arg command argument
     * @throws IOException                                            if unexpected error occurs
     * @throws NoArgumentException                                    if command requires argument but none were given
     * @throws InvalidArgumentsWhileVehicleBuildingViaScriptException if invalid arguments given for building vehicle via script
     */
    String execute(String arg) throws IOException, NoArgumentException, InvalidArgumentsWhileVehicleBuildingViaScriptException;
}
