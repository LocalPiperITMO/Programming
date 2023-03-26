package commands;

import receivers.CollectionProcessingCommandReceiver;

import java.io.IOException;

public class ClearCommand implements Command {
    private final CollectionProcessingCommandReceiver receiver;

    /**
     * "clear" command
     * Clears the collection
     *
     * @param receiver used for storing the collection
     */
    public ClearCommand(CollectionProcessingCommandReceiver receiver) {
        this.receiver = receiver;
    }

    /**
     * Used for showing information.
     *
     * @return information about the command
     */

    public String showInfo() {
        return "No argument required. Clears the dataset";
    }

    /**
     * Executes command
     *
     * @param arg command argument
     * @throws IOException if unexpected error occurs
     */
    public String execute(String arg) throws IOException {
        return receiver.clear();
    }
}
