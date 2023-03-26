package commands;

import exceptions.NoArgumentException;
import receivers.SimpleArgumentCommandReceiver;

import java.io.IOException;

public class RemoveByIDCommand implements Command {
    private final SimpleArgumentCommandReceiver receiver;

    /**
     * "remove_by_id id" command
     * Removes element with given ID from the collection, if one exists
     *
     * @param receiver used for storing the collection
     */
    public RemoveByIDCommand(SimpleArgumentCommandReceiver receiver) {
        this.receiver = receiver;
    }

    /**
     * Used for showing information.
     *
     * @return information about the command
     */
    public String showInfo() {
        return "Required argument - ID(numeric). Removes an element with the given ID";
    }

    /**
     * Executes command
     *
     * @param argument command argument
     * @throws IOException         if unexpected error occurs
     * @throws NoArgumentException if command requires argument, but none were given
     */
    public String execute(String argument) throws IOException, NoArgumentException {
        if (argument.length() != 0) {
            int id = Integer.parseInt(argument);
            return receiver.removeByID(id);
        } else {
            throw new NoArgumentException();
        }
    }
}
