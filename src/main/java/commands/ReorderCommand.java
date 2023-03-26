package commands;

import receivers.SortingCommandReceiver;

public class ReorderCommand implements Command {
    private final SortingCommandReceiver receiver;

    /**
     * "reorder" command
     * Reverses the element order of the collection
     *
     * @param receiver used for storing the collection
     */
    public ReorderCommand(SortingCommandReceiver receiver) {
        this.receiver = receiver;
    }

    /**
     * Used for showing information.
     *
     * @return information about the command
     */
    public String showInfo() {
        return "No argument required. Displays every element of the dataset in reverse order of the current sorting";
    }

    /**
     * Executes command
     *
     * @param arg command argument
     */
    public String execute(String arg) {
        return receiver.reorder();
    }
}
