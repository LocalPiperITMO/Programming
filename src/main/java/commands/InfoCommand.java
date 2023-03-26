package commands;

import receivers.DisplayingCommandReceiver;

public class InfoCommand implements Command {
    private final DisplayingCommandReceiver receiver;

    /**
     * "info" command
     * Displays information about the collection (collection type, creation date, number of elements)
     *
     * @param receiver used for storing the collection
     */
    public InfoCommand(DisplayingCommandReceiver receiver) {
        this.receiver = receiver;
    }

    /**
     * Used for showing information.
     *
     * @return information about the command
     */
    public String showInfo() {
        return "No argument required. Displays the information about the dataset (type of dataset, creation date, number of elements)";
    }

    /**
     * Executes command
     *
     * @param arg command argument
     */
    public String execute(String arg) {
        return receiver.info();
    }
}
