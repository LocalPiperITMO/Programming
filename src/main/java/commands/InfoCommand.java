package commands;

import pattern.Command;
import pattern.Receiver;

import java.time.LocalDate;

public class InfoCommand implements Command {
    private final Receiver receiver;

    public InfoCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    public void execute(String arg) {
        System.out.println("Dataset type: " + receiver.dataSet().getClass().getName() + "\nDataset creation date: " + LocalDate.now()
                + "\nDataset number of elements: " + receiver.dataSet().size());
    }
}
