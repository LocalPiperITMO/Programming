package commands;

import receivers.CollectionProcessingCommandReceiver;

import java.io.IOException;

/**
 * Command for saving collection to the file it was taken from
 */
public class SaveCommand implements Command {

    private final CollectionProcessingCommandReceiver receiver;

    /**
     * @param receiver receiver with command realization
     */
    public SaveCommand(CollectionProcessingCommandReceiver receiver) {
        this.receiver = receiver;
    }

    /**
     * @return information about the command
     */
    public String showInfo() {
        return "No argument required. Saves the dataset to file";
    }

    /**
     * Calls method from the receiver
     *
     * @param arg command argument
     * @return report on command execution
     */
    public String execute(String arg) throws IOException {
        return receiver.save();
    }

}


