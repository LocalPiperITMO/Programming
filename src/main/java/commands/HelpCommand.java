package commands;

import pattern.Command;
import pattern.Receiver;

public class HelpCommand implements Command {
    private final Receiver receiver;

    public HelpCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    public void execute() {
        receiver.help();
    }
}
