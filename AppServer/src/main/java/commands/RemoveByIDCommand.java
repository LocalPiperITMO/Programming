package commands;

import receivers.SimpleArgumentCommandReceiver;

import java.io.Serializable;

/**
 * Command for removing existing elements
 */
public class RemoveByIDCommand implements Command {
    private final int id;

    public RemoveByIDCommand(int id) {
        this.id = id;
    }



    public Serializable execute() {
        SimpleArgumentCommandReceiver receiver = new SimpleArgumentCommandReceiver();
        return receiver.removeByID(id);
    }
}
