package commands;

import exceptions.NoArgumentException;
import pattern.Command;
import pattern.Receiver;
import validators.UserRequestValidator;

import java.io.IOException;

public class FilterByFuelConsumptionCommand implements Command {
    private final UserRequestValidator validator = new UserRequestValidator();
    private final Receiver receiver;

    public FilterByFuelConsumptionCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    public void execute() throws IOException, NoArgumentException {
        try {
            throw new NoArgumentException();
        } catch (NoArgumentException noArgument) {
            validator.noArgumentCommandRequest(this.getClass().getName());
        }
    }

    public void execute(String argument) {

        receiver.filterByFuelConsumption(Long.parseLong(argument));
    }
}
