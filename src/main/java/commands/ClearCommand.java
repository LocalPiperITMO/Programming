package commands;

import java.io.IOException;

public class ClearCommand implements Command {
    private final Receiver receiver;

    /**
     * "clear" command
     * Clears the collection
     *
     * @param receiver used for storing the collection
     */
    public ClearCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    /**
     * Used for showing information.
     *
     * @return information about the command
     */

    public String showInfo() {
        return "No argument required. Clears the dataset";
    }

    /**
     * Executes command
     *
     * @param arg              command argument
     * @param isCalledByScript checks if command called from script
     * @throws IOException if unexpected error occurs
     */
    public void execute(String arg, boolean isCalledByScript) throws IOException {
        receiver.dataSet().removeAllElements();
        System.out.println("Collection has been emptied");
    }
}
