package commands;

import pattern.Command;
import pattern.Receiver;

public class PrintAscendingCommand implements Command {
    private final Receiver receiver;

    public PrintAscendingCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    public void execute() {
        receiver.printAscending();
    }
}
