package pattern;

import exceptions.NoArgumentException;

import java.io.IOException;

public interface Command {
    void execute() throws IOException, NoArgumentException;

    void execute(String arg) throws IOException, NoArgumentException;
}
