package commands;

import receivers.CollectionModifyingCommandReceiver;

import java.io.IOException;

/**
 * Command for adding elements to the collection.
 */
public class AddElementCommand implements Command {

    private final CollectionModifyingCommandReceiver receiver;

    /**
     * @param receiver receiver with command realization
     */
    public AddElementCommand(CollectionModifyingCommandReceiver receiver) {
        this.receiver = receiver;
    }

    /**
     * @return information about the command
     */
    public String showInfo() {
        return "No argument required. Adds pre-built element to dataset. May call user to build element manually";
    }

    /**
     * Calls method from the receiver
     *
     * @param arg command argument
     * @return report on command execution
     */
    public String execute(String arg) throws IOException {
        return receiver.addVehicle();
    }
}
