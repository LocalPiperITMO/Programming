package commands;

import receivers.CollectionProcessingCommandReceiver;

import java.io.IOException;

public class SaveCommand implements Command {
    private final CollectionProcessingCommandReceiver receiver;

    /**
     * "save" command
     * Saves the collection to the file it was taken from
     *
     * @param receiver used for storing both the collection and the path to file
     */
    public SaveCommand(CollectionProcessingCommandReceiver receiver) {
        this.receiver = receiver;
    }

    /**
     * Used for showing information.
     *
     * @return information about the command
     */
    public String showInfo() {
        return "No argument required. Saves the dataset to file";
    }

    /**
     * Executes command
     *
     * @param arg command argument
     * @throws IOException if unexpected error occurs
     */
    public String execute(String arg) throws IOException {
        return receiver.save();
    }

}


