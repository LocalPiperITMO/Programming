package clienthandlingfacade;

import receivers.TextReceiver;
import tasks.ProcessingTask;
import wrapping.CommandDescriptionPacket;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

public class ClientDataProcessor {
    private final ForkJoinPool forkJoinPool;
    private final TextReceiver receiver = new TextReceiver();

    public ClientDataProcessor(ForkJoinPool processingPool) {
        this.forkJoinPool = processingPool;
    }

    public List<Serializable> processUserPackets(List<CommandDescriptionPacket> packetList) {
        receiver.printToLog(this.getClass().getSimpleName(),"Processing packet list...");
        ProcessingTask processingTask = new ProcessingTask(packetList, 0, packetList.size());
        return forkJoinPool.invoke(processingTask);
    }
}
