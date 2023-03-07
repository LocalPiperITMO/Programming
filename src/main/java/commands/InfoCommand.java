package commands;

import java.time.LocalDate;

public class InfoCommand implements Command {
    private final Receiver receiver;

    public InfoCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    public String showInfo() {
        return "No argument required. Displays the information about the dataset (type of dataset, creation date, number of elements)";
    }

    public void execute(String arg) {
        System.out.println("Dataset type: " + receiver.dataSet().getClass().getName() + "\nDataset creation date: " + LocalDate.now()
                + "\nDataset number of elements: " + receiver.dataSet().size());
    }
}
