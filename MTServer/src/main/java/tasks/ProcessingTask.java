package tasks;

import clienthandlingfacade.ClientAuthorizer;
import commandprocessing.PacketHandler;
import exceptions.UnauthorizedAccessException;
import wrapping.CommandDescriptionPacket;
import wrapping.PacketWrapper;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class ProcessingTask extends RecursiveTask<List<Serializable>> {
    private final List<CommandDescriptionPacket> packetList;
    private final int start;
    private final int end;

    public ProcessingTask(List<CommandDescriptionPacket> packetList, int start, int end) {
        this.packetList = packetList;
        this.start = start;
        this.end = end;
    }

    @Override
    protected List<Serializable> compute() {
        int size = end - start;
        if (size <= 10) {
            try {
                return process(packetList, start, end);
            } catch (SQLException | UnauthorizedAccessException | NoSuchAlgorithmException e) {
                List<Serializable> report = new ArrayList<>();
                report.add(new PacketWrapper().wrapReport("ACCESS DENIED: UNABLE TO IDENTIFY USER"));
                return report;
            }
        } else {
            int mid = start + size / 2;
            ProcessingTask leftTask = new ProcessingTask(packetList, start, mid);
            ProcessingTask rightTask = new ProcessingTask(packetList, mid, end);
            invokeAll(leftTask, rightTask);
            List<Serializable> result = new ArrayList<>();
            result.addAll(leftTask.join());
            result.addAll(rightTask.join());
            return result;
        }
    }

    private List<Serializable> process(List<CommandDescriptionPacket> packetList, int start, int end) throws SQLException, UnauthorizedAccessException, NoSuchAlgorithmException {
        PacketHandler handler = new PacketHandler();
        ClientAuthorizer clientAuthorizer = new ClientAuthorizer();
        for (int i = start; i < end; ++i) {
            if (clientAuthorizer.authorizeClient(packetList.get(i))) {
                handler.handleUserPacket(packetList.get(i));
            }
        }
        return handler.getListOfPackets();
    }
}
