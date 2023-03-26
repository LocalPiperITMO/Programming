package commands;

import receivers.DisplayingCommandReceiver;

public class ShowCommand implements Command {
    private final DisplayingCommandReceiver receiver;

    /**
     * "show" command
     * Displays the collection
     *
     * @param receiver used for storing the collection
     */
    public ShowCommand(DisplayingCommandReceiver receiver) {
        this.receiver = receiver;
    }

    /**
     * Used for showing information.
     *
     * @return information about the command
     */
    public String showInfo() {
        return "No argument required. Displays every element of the dataset";
    }

    /**
     * Executes command
     *
     * @param arg command argument
     */
    public String execute(String arg) {
        return receiver.show();
    }
}
