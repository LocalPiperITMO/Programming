package commands;

import pattern.Command;
import pattern.Receiver;

import java.io.IOException;

public class ClearCommand implements Command {
    private final Receiver receiver;

    public ClearCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    public void execute() {
        receiver.clear();
    }

    @Override
    public void execute(String arg) throws IOException {
        System.out.println(this.getClass().getName() + " does not require any arguments to work.");
        execute();
    }
}
