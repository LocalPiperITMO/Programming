package commands;

import pattern.Command;
import pattern.Receiver;


public class ReorderCommand implements Command {
    private final Receiver receiver;

    public ReorderCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    public void execute() {
        receiver.reorder();
    }
}
