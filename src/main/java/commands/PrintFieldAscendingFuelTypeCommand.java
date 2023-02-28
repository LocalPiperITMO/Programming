package commands;

import pattern.Command;
import pattern.Receiver;

public class PrintFieldAscendingFuelTypeCommand implements Command {
    private final Receiver receiver;

    public PrintFieldAscendingFuelTypeCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    public void execute() {
        receiver.printFieldAscendingFuelType();
    }
    @Override
    public void execute(String arg) {
        System.out.println(this.getClass().getName() + " does not require any arguments to work.");
        execute();
    }
}
