package commands;

import exceptions.NoArgumentException;
import receivers.SimpleArgumentCommandReceiver;

import java.io.IOException;

/**
 * Command for removing existing elements
 */
public class RemoveByIDCommand implements Command {
    /**
     * Receiver that contains required method for the command
     */
    private final SimpleArgumentCommandReceiver receiver;

    /**
     * @param receiver receiver with command realization
     */
    public RemoveByIDCommand(SimpleArgumentCommandReceiver receiver) {
        this.receiver = receiver;
    }

    /**
     * @return information about the command
     */
    public String showInfo() {
        return "Required argument - ID(numeric). Removes an element with the given ID";
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
            return receiver.removeByID(id);
        } else {
            throw new NoArgumentException();
        }
    }
}
