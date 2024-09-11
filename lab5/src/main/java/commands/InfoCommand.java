package commands;

import receivers.DisplayingCommandReceiver;

/**
 * Command for printing information about the collection.
 * Shows number of elements, creation date and collection type
 */
public class InfoCommand implements Command {

    private final DisplayingCommandReceiver receiver;

    /**
     * @param receiver receiver with command realization
     */
    public InfoCommand(DisplayingCommandReceiver receiver) {
        this.receiver = receiver;
    }

    /**
     * @return information about the command
     */
    public String showInfo() {
        return "No argument required. Displays the information about the dataset (type of dataset, creation date, number of elements)";
    }

    /**
     * Calls method from the receiver
     *
     * @param arg command argument
     * @return report on command execution
     */
    public String execute(String arg) {
        return receiver.info();
    }
}
