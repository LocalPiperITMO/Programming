package commands;

import exceptions.NoArgumentException;
import pattern.Command;
import pattern.Receiver;

import java.io.IOException;
import java.util.Objects;

public class RemoveByIDCommand implements Command {
    private final Receiver receiver;

    public RemoveByIDCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    public void execute() throws IOException, NoArgumentException {
        throw new NoArgumentException();

    }

    public void execute(String argument) throws IOException, NoArgumentException {
        if (Objects.equals(argument, "")) {
            execute();
        } else {
            receiver.remove_by_id(Integer.parseInt(argument));
        }
    }
}
