package commands;

import pattern.Command;
import pattern.Receiver;

import java.io.IOException;

public class RemoveGreaterElementsCommand implements Command {
    private final Receiver receiver;

    public RemoveGreaterElementsCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    public void execute() throws IOException {
        receiver.removeGreaterElements();
    }

    @Override
    public void execute(String arg) throws IOException {
        System.out.println(this.getClass().getName() + " does not require any arguments to work.");
        execute();
    }
}