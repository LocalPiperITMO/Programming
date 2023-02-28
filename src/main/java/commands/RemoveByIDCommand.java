package commands;

import exceptions.NoArgumentException;
import pattern.Command;
import pattern.Receiver;
import validators.UserRequestValidator;

import java.io.IOException;
import java.util.Objects;

public class RemoveByIDCommand implements Command {
    private final UserRequestValidator validator = new UserRequestValidator();
    private final Receiver receiver;
    private String argument;

    public RemoveByIDCommand(Receiver receiver, String argument) {
        this.receiver = receiver;
        this.argument = argument;
    }

    public void execute() throws IOException, NoArgumentException {
        throw new NoArgumentException();

    }

    public void execute(String argument) throws IOException, NoArgumentException {
        if (Objects.equals(argument, "")) {
            execute();
        } else {
            receiver.info();
        }
    }
}
