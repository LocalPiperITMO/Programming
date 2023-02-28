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
    @Override
    public void execute(String arg) {
        System.out.println(this.getClass().getName() + " does not require any arguments to work.");
        execute();
    }
}
