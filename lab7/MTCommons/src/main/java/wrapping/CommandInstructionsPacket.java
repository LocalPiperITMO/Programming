package wrapping;

import java.io.Serializable;
import java.util.HashMap;

public class CommandInstructionsPacket implements Serializable {
    private final HashMap<String, String[]> commandHashMap;

    public CommandInstructionsPacket(HashMap<String, String[]> map) {
        this.commandHashMap = map;
    }

    public HashMap<String, String[]> getCommandHashMap() {
        return commandHashMap;
    }
}
