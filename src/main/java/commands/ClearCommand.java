package commands;

import java.io.IOException;

public class ClearCommand implements Command {
    private final Receiver receiver;

    public ClearCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    public String showInfo() {
        return "No argument required. Clears the dataset";
    }

    public void execute(String arg) throws IOException {
        receiver.dataSet().removeAllElements();
        System.out.println("Collection has been emptied");
    }
}
