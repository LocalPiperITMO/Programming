package commands;

import pattern.Command;
import pattern.Receiver;

public class ClearCommand implements Command {
    private final Receiver receiver;

    public ClearCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    public void execute() {
        receiver.clear();
    }
}
