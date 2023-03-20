package commands;

import receivers.SortingCommandReceiver;

public class PrintAscendingCommand implements Command {
    private final SortingCommandReceiver receiver;

    /**
     * "print_ascending" command
     * Sorts the list in ascending order, then prints it
     * Sorting is done by comparing sums of integer arguments of element
     *
     * @param receiver used for storing the collection
     */
    public PrintAscendingCommand(SortingCommandReceiver receiver) {
        this.receiver = receiver;
    }

    /**
     * Used for showing information.
     *
     * @return information about the command
     */
    public String showInfo() {
        return "No argument required. Displays every element sorted by ID";
    }

    /**
     * Executes command
     *
     * @param arg command argument
     */
    public void execute(String arg) {
        receiver.printAscending();
    }
}
