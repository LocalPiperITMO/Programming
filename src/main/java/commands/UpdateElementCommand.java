package commands;

import exceptions.NoArgumentException;
import receivers.CollectionModifyingCommandReceiver;

import java.io.IOException;

/**
 * Command for rewriting existing elements.
 * Has 2 different realizations: by user input and by script arguments
 */
public class UpdateElementCommand implements Command {
    /**
     * Receiver that contains required method for the command
     */
    private final CollectionModifyingCommandReceiver receiver;

    /**
     * @param receiver receiver with command realization
     */
    public UpdateElementCommand(CollectionModifyingCommandReceiver receiver) {
        this.receiver = receiver;
    }

    /**
     * @return information about the command
     */
    public String showInfo() {
        return "Required argument - ID(numeric). User builds an element. Updates the element with the given ID";
    }

    /**
     * Calls method from the receiver
     *
     * @param arg command argument
     * @return report on command execution
     * @throws NoArgumentException if no argument was given
     */
    public String execute(String arg) throws IOException, NoArgumentException {
        if (arg.length() != 0) {
            int id = Integer.parseInt(arg);
            return receiver.update(id);
        } else {
            throw new NoArgumentException();
        }
    }
}