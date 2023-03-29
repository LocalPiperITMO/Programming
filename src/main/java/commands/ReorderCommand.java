package commands;

import receivers.SortingCommandReceiver;

/**
 * Command for printing elements in reversed order
 */
public class ReorderCommand implements Command {
    /**
     * Receiver that contains required method for the command
     */
    private final SortingCommandReceiver receiver;

    /**
     * @param receiver receiver with command realization
     */
    public ReorderCommand(SortingCommandReceiver receiver) {
        this.receiver = receiver;
    }

    /**
     * @return information about the command
     */
    public String showInfo() {
        return "No argument required. Displays every element of the dataset in reverse order of the current sorting";
    }

    /**
     * Calls method from the receiver
     *
     * @param arg command argument
     * @return report on command execution
     */
    public String execute(String arg) {
        return receiver.reorder();
    }
}
