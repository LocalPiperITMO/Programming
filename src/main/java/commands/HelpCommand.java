package commands;

import receivers.DisplayingCommandReceiver;

/**
 * Command for printing information about every existing command
 */
public class HelpCommand implements Command {
    /**
     * Receiver that contains required method for the command
     */
    private final DisplayingCommandReceiver receiver;
    /**
     * @param receiver receiver with command realization
     */
    public HelpCommand(DisplayingCommandReceiver receiver) {
        this.receiver = receiver;
    }

    /**
     * @return information about the command
     */
    public String showInfo() {
        return "No argument required. Displays this message";
    }

    /**
     * Calls method from the receiver
     *
     * @param arg command argument
     * @return report on command execution
     */
    public String execute(String arg) {
        return receiver.help();
    }
}
