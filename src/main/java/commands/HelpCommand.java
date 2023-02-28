package commands;

import pattern.Command;
import pattern.Receiver;

import java.io.IOException;

public class HelpCommand implements Command {
    private final Receiver receiver;

    public HelpCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    public void execute() {
        receiver.help();
    }

    @Override
    public void execute(String arg) throws IOException {
        System.out.println(this.getClass().getName() + " does not require any arguments to work.");
        execute();
    }
}
