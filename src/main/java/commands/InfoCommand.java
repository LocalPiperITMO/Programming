package commands;

import pattern.Command;
import pattern.Receiver;

public class InfoCommand implements Command {
    private final Receiver receiver;

    public InfoCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    public void execute() {
        receiver.info();
    }
}
