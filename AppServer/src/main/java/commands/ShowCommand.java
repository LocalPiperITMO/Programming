package commands;

import receivers.DisplayingCommandReceiver;

import java.io.Serializable;

/**
 * Command for printing the collection
 */
public class ShowCommand implements Command {



    public Serializable execute() {
        DisplayingCommandReceiver receiver = new DisplayingCommandReceiver();
        return receiver.show();
    }
}
