package commands;

import exceptions.NoArgumentException;
import pattern.Command;
import pattern.Receiver;

import java.io.IOException;
import java.util.Objects;

public class UpdateByIDCommand implements Command {
    private final Receiver receiver;

    public UpdateByIDCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    public void execute() throws IOException, NoArgumentException {
        throw new NoArgumentException();

    }

    public void execute(String argument) throws IOException, NoArgumentException {
        if (Objects.equals(argument, "")) {
            execute();
        } else {
            receiver.updateByID(Integer.parseInt(argument));
        }
    }
}
