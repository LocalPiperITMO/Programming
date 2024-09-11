package commandlistmanagement;

import receivers.TextReceiver;
import wrapping.CommandInstructionsPacket;
import wrapping.PacketWrapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandListPreparer {

    public static Map<String, String> commandInfoConnector = new HashMap<>();
    private final TextReceiver receiver = new TextReceiver();

    public void prepareServerCommandList(List<List<String>> serverCommandList) {
        for (int i = 0; i < serverCommandList.get(0).size(); ++i) {
            commandInfoConnector.put(serverCommandList.get(0).get(i), serverCommandList.get(1).get(i));
        }
    }

    public CommandInstructionsPacket prepareClientCommandList(List<List<String>> clientCommandList) {
        HashMap<String, String[]> clientMap = new HashMap<>();
        for (int i = 0; i < clientCommandList.get(0).size(); ++i) {
            String[] commandArgs = clientCommandList.get(1).get(i).split(",");
            if (commandArgs.length == 2) {
                clientMap.put(clientCommandList.get(0).get(i), commandArgs);
            } else {
                receiver.printToLog(this.getClass().getSimpleName(), "Invalid number of arguments: " + clientCommandList.get(1).get(i));
            }
        }
        return new PacketWrapper().wrapCIP(clientMap);
    }

}
