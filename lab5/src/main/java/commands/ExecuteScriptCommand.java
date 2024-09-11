package commands;

import exceptions.NoArgumentException;
import receivers.ExecuteScriptCommandReceiver;

import java.io.IOException;

/**
 * Command for executing script. Script should contain commands and (if needed) arguments for building
 * May not execute everything if syntax errors are present
 */
public class ExecuteScriptCommand implements Command {

    private final ExecuteScriptCommandReceiver receiver;

    /**
     * @param receiver receiver with command realization
     */
    public ExecuteScriptCommand(ExecuteScriptCommandReceiver receiver) {
        this.receiver = receiver;

    }

    /**
     * @return information about the command
     */
    public String showInfo() {
        return "Required argument - nameOfScript(String). Takes the script from the text and executes it. Use with caution";
    }

    /**
     * Calls method from the receiver
     *
     * @param filePath path to the script
     * @return report on command execution
     * @throws NoArgumentException if no argument was given
     */
    public String execute(String filePath) throws IOException, NoArgumentException {
        return receiver.execute(filePath);
    }
}