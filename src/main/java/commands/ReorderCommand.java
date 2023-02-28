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
    @Override
    public void execute(String arg) {
        System.out.println(this.getClass().getName() + " does not require any arguments to work.");
        execute();
    }
}
