package commands;

import java.time.LocalDate;

public class InfoCommand implements Command {
    private final Receiver receiver;

    /**
     * "info" command
     * Displays information about the collection (collection type, creation date, number of elements)
     *
     * @param receiver used for storing the collection
     */
    public InfoCommand(Receiver receiver) {
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
     * @param arg              command argument
     * @param isCalledByScript checks if command called from script
     */
    public void execute(String arg, boolean isCalledByScript) {
        System.out.println("Dataset type: " + receiver.dataSet().getClass().getName() + "\nDataset creation date: " + LocalDate.now()
                + "\nDataset number of elements: " + receiver.dataSet().size());
    }
}
