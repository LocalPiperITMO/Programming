package commands;

import exceptions.NoArgumentException;
import receivers.ExecuteScriptCommandReceiver;

import java.io.IOException;

public class ExecuteScriptCommand implements Command {
    private final ExecuteScriptCommandReceiver receiver;

    /**
     * "execute_script script" command
     * Executes script from path given as argument
     */
    public ExecuteScriptCommand(ExecuteScriptCommandReceiver receiver) {
        this.receiver = receiver;

    }

    /**
     * Used for showing information.
     *
     * @return information about the command
     */
    public String showInfo() {
        return "Required argument - nameOfText(String). Takes the script from the text and executes it";
    }

    /**
     * Executes command
     *
     * @param fileName name of script (command argument)
     * @throws IOException         if unexpected error occurs
     * @throws NoArgumentException if command requires argument but none were given
     */
    public String execute(String fileName) throws IOException, NoArgumentException {
        return receiver.executeScript(fileName);
    }
}