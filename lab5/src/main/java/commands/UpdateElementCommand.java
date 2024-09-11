package commands;

import org.apache.commons.lang3.math.NumberUtils;
import receivers.CollectionModifyingCommandReceiver;
import receivers.TextReceiver;

/**
 * Command for rewriting existing elements.
 */
public class UpdateElementCommand implements Command {

    private final CollectionModifyingCommandReceiver receiver;
    private final TextReceiver textReceiver;

    /**
     * @param receiver receiver with command realization
     */
    public UpdateElementCommand(CollectionModifyingCommandReceiver receiver) {
        this.receiver = receiver;
        this.textReceiver = new TextReceiver();
    }

    /**
     * @return information about the command
     */
    public String showInfo() {
        return "Required argument - ID(numeric). Updates the element with the given ID, if one exists. May call user to build element manually";
    }

    /**
     * Checks if the given id is valid and used by existing elements
     * @param arg ID
     * @return true if element with the given ID exists, false otherwise
     */
    public boolean checkIfValidID(String arg) {
        if (arg.length() != 0) {
            if (NumberUtils.isParsable(arg)) {
                try {
                    int ID = Integer.parseInt(arg);
                    if (ID >= 0 && ID < 1000000) {
                        if (receiver.getStorage().getIdGenerator().getIdSet().contains(ID)) {
                            return true;
                        }
                        textReceiver.print("Update failed: element with the given ID does not exist");
                        return false;
                    }
                    textReceiver.print("Update failed: element does not belong to bounds [0,1000000)");
                    return false;
                } catch (NumberFormatException numberFormatException) {
                    textReceiver.print("Update failed: element does not belong to bounds [0,1000000)");
                    return false;
                }
            }
            textReceiver.print("Update failed: required type int, but String was given");
            return false;
        }
        textReceiver.print("Update failed: no argument was given");
        return false;
    }

    /**
     * Calls method from the receiver
     *
     * @param arg command argument
     * @return report on command execution
     */
    public String execute(String arg) {
        return receiver.update(Integer.parseInt(arg));
    }
}