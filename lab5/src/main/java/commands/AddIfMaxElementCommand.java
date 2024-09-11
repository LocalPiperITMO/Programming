package commands;

import receivers.CollectionModifyingCommandReceiver;

import java.io.IOException;

/**
 * Command for adding elements to the collection if the condition (greater than the greatest) is matched.
 */
public class AddIfMaxElementCommand implements Command {

    private final CollectionModifyingCommandReceiver receiver;

    /**
     * @param receiver receiver with command realization
     */
    public AddIfMaxElementCommand(CollectionModifyingCommandReceiver receiver) {
        this.receiver = receiver;
    }

    /**
     * @return information about the command
     */
    public String showInfo() {
        return "No argument required. If the element is greater that the greatest element in the dataset, it is added to dataset. May call user to build element manually";
    }

    /**
     * Calls method from the receiver
     *
     * @param arg command argument
     * @return report on command execution
     */
    public String execute(String arg) throws IOException {
        return receiver.addIfMax();
    }
}