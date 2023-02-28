package commands;

import exceptions.NoArgumentException;
import pattern.Command;
import pattern.Receiver;

import java.io.IOException;
import java.util.Objects;

public class UpdateElementCommand implements Command {
    private final Receiver receiver;

    public UpdateElementCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    public void execute() throws IOException, NoArgumentException {
        throw new NoArgumentException();

    }

    public void execute(String argument) throws IOException, NoArgumentException {
        if (Objects.equals(argument, "")) {
            execute();
        } else {
            receiver.update(Integer.parseInt(argument));
        }
    }
}