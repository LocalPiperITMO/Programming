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
    @Override
    public void execute(String arg) {
        System.out.println(this.getClass().getName() + " does not require any arguments to work.");
        execute();
    }
}
