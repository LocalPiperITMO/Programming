package commands;

import receivers.DisplayingCommandReceiver;

public class HelpCommand implements Command {
    private final DisplayingCommandReceiver receiver;

    public HelpCommand(DisplayingCommandReceiver receiver) {
        this.receiver = receiver;
    }

    public String showInfo() {
        return "No argument required. Displays this message";
    }

    /**
     * Executes command
     *
     * @param arg command argument
     */
    public void execute(String arg) {
        receiver.help();
    }
}
