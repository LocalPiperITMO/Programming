package commands;

import java.io.Serializable;

/**
 * Command interface. Has 2 methods: showInfo() and execute(String arg).
 */
@FunctionalInterface
public interface Command {

    /**
     * Calls method from the receiver
     *
     * @return report on command execution
     */
    Serializable execute();
}
