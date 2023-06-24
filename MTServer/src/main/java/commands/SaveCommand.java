package commands;

import wrapping.PacketWrapper;

import java.io.Serializable;

public class SaveCommand implements Command {



    @Override
    public Serializable execute() {
        return new PacketWrapper().wrapReport("Goodbye!");
    }
}
