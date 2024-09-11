package utils;

import receivers.TextReceiver;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConfigDataGrabber {
    private final TextReceiver receiver = new TextReceiver();
    private String dbURL = "";
    private String username = "";
    private String password = "";

    public void grabData(String configPath) {
        List<String> dbData = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(configPath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                dbData.add(line);
            }
        } catch (IOException e) {
            receiver.printToLog("ERROR", "Something went wrong trying to get config data");
        }
        parseData(dbData);
    }

    private void parseData(List<String> dbData) {
        try {
            dbURL = dbData.get(0);
            username = dbData.get(1);
            password = dbData.get(2);
        } catch (IndexOutOfBoundsException e) {
            receiver.printToLog("ERROR", "Not enough data in the config file");
        }

    }

    public String getDbURL() {
        return dbURL;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
