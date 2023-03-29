package commands;

import receivers.CollectionProcessingCommandReceiver;

import java.io.IOException;

/**
 * Command for clearing the collection.
 */
public class ClearCommand implements Command {
    /**
     * Receiver that contains required method for the command
     */
    private final CollectionProcessingCommandReceiver receiver;
    /**
     * @param receiver receiver with command realization
     */
    public ClearCommand(CollectionProcessingCommandReceiver receiver) {
        this.receiver = receiver;
    }

    /**
     * @return information about the command
     */

    public String showInfo() {
        return "No argument required. Clears the dataset";
    }

    /**
     * Calls method from the receiver
     *
     * @param arg command argument
     * @return report on command execution
     */
    public String execute(String arg) throws IOException {
        return receiver.clear();
    }
}
