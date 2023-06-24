package commands;

import java.io.Serializable;

public class ExitCommand implements Command {
    @Override
    public Serializable execute() {
        return new SaveCommand().execute();
    }
}
