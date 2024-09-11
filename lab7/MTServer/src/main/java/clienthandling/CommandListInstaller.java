package clienthandling;

import commandlistmanagement.CommandListDivider;
import commandlistmanagement.CommandListGetter;
import commandlistmanagement.CommandListPreparer;
import receivers.TextReceiver;
import wrapping.CommandInstructionsPacket;

public class CommandListInstaller {
    private CommandInstructionsPacket clientInstructionsPacket;
    private final TextReceiver receiver = new TextReceiver();

    public void prepareCommands(String commandListPath) {
        receiver.printToLog(this.getClass().getSimpleName(), "Receiving list of commands...");

        CommandListGetter getter = new CommandListGetter();
        CommandListDivider divider = new CommandListDivider();
        CommandListPreparer preparer = new CommandListPreparer();

        divider.divideCommandList(getter.getCommandList(commandListPath));
        preparer.prepareServerCommandList(divider.getServerSideCommandList());
        clientInstructionsPacket = preparer.prepareClientCommandList(divider.getClientSideCommandList());

        receiver.printToLog(this.getClass().getSimpleName(), "List of commands received");
    }

    public CommandInstructionsPacket getClientInstructionsPacket() {
        return clientInstructionsPacket;
    }
}
