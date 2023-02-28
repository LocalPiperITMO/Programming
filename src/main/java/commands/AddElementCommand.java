package commands;

import pattern.Command;
import pattern.Receiver;

import java.io.IOException;

public class AddElementCommand implements Command {
    private final Receiver receiver;

    public AddElementCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    public void execute() throws IOException {
        receiver.add();
    }

    @Override
    public void execute(String arg) throws IOException {
        System.out.println(this.getClass().getName() + " does not require any arguments given in this line.");
        execute();
    }
}
