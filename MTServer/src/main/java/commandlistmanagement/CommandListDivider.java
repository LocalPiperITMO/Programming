package commandlistmanagement;

import java.util.ArrayList;
import java.util.List;

public class CommandListDivider {
    private final List<List<String>> clientSideCommandList = new ArrayList<>();
    private final List<List<String>> serverSideCommandList = new ArrayList<>();
    public void divideCommandList(List<List<String>> commandList) {
        clientSideCommandList.add(commandList.get(0));
        clientSideCommandList.add(commandList.get(1));
        serverSideCommandList.add(commandList.get(0));
        serverSideCommandList.add(commandList.get(2));
    }

    public List<List<String>> getClientSideCommandList() {
        return clientSideCommandList;
    }

    public List<List<String>> getServerSideCommandList() {
        return serverSideCommandList;
    }
}
