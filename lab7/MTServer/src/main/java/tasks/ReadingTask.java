package tasks;

import wrapping.CommandDescriptionPacket;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;


public class ReadingTask extends RecursiveTask<List<CommandDescriptionPacket>> {
    private final byte[] data;
    private final int start;
    private final int end;

    public ReadingTask(byte[] data, int start, int end) {
        this.data = data;
        this.start = start;
        this.end = end;
    }

    private List<CommandDescriptionPacket> deserialize(byte[] data, int start, int end) {
        List<CommandDescriptionPacket> result = new ArrayList<>();
        try (ByteArrayInputStream bis = new ByteArrayInputStream(data, start, end - start);
             ObjectInputStream ois = new ObjectInputStream(bis)) {
            Object obj = ois.readObject();
            if (obj instanceof CommandDescriptionPacket packet) {
                result.add(packet);
            } else if (obj instanceof List<?> packetList) {
                for (Object elem : packetList) {
                    if (elem instanceof CommandDescriptionPacket packet) {
                        result.add(packet);
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected List<CommandDescriptionPacket> compute() {
        int THRESHOLD = 10000;
        if (end - start < THRESHOLD) {
            return deserialize(data, start, end);
        } else {
            List<ReadingTask> subTasks = new ArrayList<>();
            int mid = (start + end) / 2;
            ReadingTask leftTask = new ReadingTask(data, start, mid);
            ReadingTask rightTask = new ReadingTask(data, mid, end);

            leftTask.fork();
            rightTask.fork();

            subTasks.add(leftTask);
            subTasks.add(rightTask);

            List<CommandDescriptionPacket> result = new ArrayList<>();
            while (!subTasks.isEmpty()) {
                int last = subTasks.size() - 1;
                ReadingTask task = subTasks.remove(last);
                if (task.end - task.start < THRESHOLD) {
                    result.addAll(task.compute());
                } else {
                    subTasks.addAll(task.createSubtasks());
                }
            }

            return result;
        }
    }

    private List<ReadingTask> createSubtasks() {
        int mid = (start + end) / 2;
        ReadingTask leftTask = new ReadingTask(data, start, mid);
        ReadingTask rightTask = new ReadingTask(data, mid, end);

        leftTask.fork();
        rightTask.fork();

        List<ReadingTask> subTasks = new ArrayList<>();
        subTasks.add(leftTask);
        subTasks.add(rightTask);

        return subTasks;
    }
}
