package commands;

import exceptions.NoArgumentException;
import org.apache.commons.lang3.math.NumberUtils;
import receivers.SimpleArgumentCommandReceiver;

import java.io.IOException;

/**
 * Command for removing existing elements
 */
public class RemoveByIDCommand implements Command {

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
        return "Required argument - ID(numeric). Removes an element with the given ID, if one exists";
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
            if (NumberUtils.isParsable(arg)) {
                try {
                    int ID = Integer.parseInt(arg);
                    if (ID >= 0 && ID < 1000000) {
                        return receiver.removeByID(ID);
                    }
                    return "Argument does not belong to bounds [0,1000000). Command execution failed";
                } catch (NumberFormatException numberFormatException) {
                    return "Argument does not belong to bounds [0,1000000). Command execution failed";
                }
            }
            return "Invalid argument type: required int, but String was given";
        } else {
            throw new NoArgumentException();
        }
    }
}
