package commands;

import exceptions.InvalidArgumentsWhileVehicleBuildingViaScriptException;
import exceptions.NoArgumentException;

import java.io.IOException;

/**
 * Command interface. Has 2 methods: showInfo() and execute(String arg).
 */
public interface Command {
    /**
     * @return information about the command
     */
    String showInfo();

    /**
     * Calls method from the receiver
     * @param arg command argument
     * @return report on command execution
     */
    String execute(String arg) throws IOException, NoArgumentException, InvalidArgumentsWhileVehicleBuildingViaScriptException;
}
