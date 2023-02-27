package commands;

import pattern.Command;
import pattern.Receiver;

public class ShowCommand implements Command {
    private final Receiver receiver;
    public ShowCommand(Receiver receiver){
        this.receiver = receiver;
    }
    public void execute(){
        receiver.show();
    }
}
