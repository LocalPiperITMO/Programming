import commands.Command;
import receivers.TextReceiver;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Invoker {
    private final List<Command> commands = new ArrayList<>();
    private final TextReceiver receiver = new TextReceiver();

    public void addCommand(Command command) {
        commands.add(command);
    }

    public List<Serializable> executeAll() {
        List<Serializable> packetList = new ArrayList<>();
        for (Command command : commands) {
            try {
                packetList.add(command.execute());
            } catch (ClassCastException e) {
                receiver.printToLog("ERROR","Can't wrap unserializable element to packet, skipping...");
            }
        }
        return packetList;
    }

    public void clearList() {
        commands.clear();
    }
}
