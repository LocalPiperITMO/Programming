package commands;

import receivers.CollectionModifyingCommandReceiver;

import java.io.IOException;
/**
 * Command for removing elements from the collection if the condition (greater than the current) is matched.
 */
public class RemoveGreaterElementsCommand implements Command {

    private final CollectionModifyingCommandReceiver receiver;

    /**
     * @param receiver receiver with command realization
     */
    public RemoveGreaterElementsCommand(CollectionModifyingCommandReceiver receiver) {
        this.receiver = receiver;
    }

    /**
     * @return information about the command
     */
    public String showInfo() {
        return "No argument required. Removes all the elements from the dataset greater than the given one. May call user to build element manually";
    }

    /**
     * Calls method from the receiver
     *
     * @param arg command argument
     * @return report on command execution
     */
    public String execute(String arg) throws IOException {
        return receiver.removeGreater();
    }
}