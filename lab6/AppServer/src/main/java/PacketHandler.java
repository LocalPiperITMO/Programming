import command_factories.FactoryConnector;
import commands.Command;
import packets.CommandDescriptionPacket;
import receivers.TextReceiver;

import java.io.Serializable;
import java.util.List;

public class PacketHandler {
    private final TextReceiver receiver = new TextReceiver();
    private final Invoker invoker = new Invoker();
    private final FactoryConnector connector = new FactoryConnector();


    public void handleUserPacket(CommandDescriptionPacket packet) {
        Command command = connector.convertPacketToCommand(packet);
        invoker.addCommand(command);
    }

    public List<Serializable> getListOfPackets() {
        receiver.printToLog(this.getClass().getSimpleName(), "Executing received commands...");
        List<Serializable> packetList = invoker.executeAll();
        receiver.printToLog(this.getClass().getSimpleName(), "Commands executed");
        receiver.printToLog(this.getClass().getSimpleName(), "Clearing command list...");
        invoker.clearList();
        receiver.printToLog(this.getClass().getSimpleName(), "Command list has been cleared");
        return packetList;
    }
}
