package commands;

import packets.PacketWrapper;
import receivers.DisplayingCommandReceiver;

import java.io.Serializable;

/**
 * Command for printing information about every existing command
 */
public class HelpCommand implements Command {




    public Serializable execute() {
        DisplayingCommandReceiver receiver = new DisplayingCommandReceiver();
        return receiver.help();
    }
}
