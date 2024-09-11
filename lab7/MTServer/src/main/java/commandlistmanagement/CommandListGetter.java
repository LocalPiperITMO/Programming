package commandlistmanagement;

import receivers.TextReceiver;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CommandListGetter {
    private final TextReceiver receiver = new TextReceiver();

    public List<List<String>> getCommandList(String path) {
        File file = new File(path);
        List<String> commands = new ArrayList<>();
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                commands.add(scanner.nextLine());
            }
        } catch (IOException e) {
            receiver.printToLog(this.getClass().getSimpleName(), "Couldn't load command list, enabling default commands");
            List<List<String>> failsafeList = new ArrayList<>();
            failsafeList.add(List.of(new String[]{"help", "show"}));
            failsafeList.add(List.of(new String[]{"null,null", "null,null"}));
            failsafeList.add(List.of(new String[]{"Displays this message", "Displays every element of the collection"}));
            return failsafeList;
        }
        return convertRawList(commands);
    }

    private List<List<String>> convertRawList(List<String> rawList) {
        List<String> commandNameList = new ArrayList<>();
        List<String> commandArgTypeList = new ArrayList<>();
        List<String> commandInfoList = new ArrayList<>();

        for (String rawString : rawList) {
            String[] parsedString = parseRawString(rawString);
            if (parsedString.length != 0) {
                commandNameList.add(parsedString[0]);
                commandArgTypeList.add(parsedString[1]);
                commandInfoList.add(parsedString[2]);
            }
        }
        List<List<String>> result = new ArrayList<>();
        result.add(commandNameList);
        result.add(commandArgTypeList);
        result.add(commandInfoList);
        return result;
    }

    private String[] parseRawString(String rawString) {
        //add:-:Vehicle,null:-:Adds element to list
        String[] result = rawString.split(":-:");
        if (result.length != 3) {
            receiver.printToLog(this.getClass().getSimpleName(), "Command list has a corrupted line: " + rawString);
            return new String[0];
        }
        return result;
    }
}
