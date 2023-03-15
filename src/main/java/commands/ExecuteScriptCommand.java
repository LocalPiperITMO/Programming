package commands;

import exceptions.NoArgumentException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ExecuteScriptCommand implements Command {
    private final Invoker invoker;
    private final List<String> complexCommandNames = new ArrayList<>();

    public ExecuteScriptCommand(Invoker invoker) {
        this.invoker = invoker;
        this.complexCommandNames.add("add");
        this.complexCommandNames.add("add_if_max");
        this.complexCommandNames.add("remove_greater");
        this.complexCommandNames.add("update");

    }

    public String showInfo() {
        return "Required argument - nameOfText(String). Takes the script from the text and executes it";
    }

    public void execute(String fileName, boolean isCalledByScript) throws IOException, NoArgumentException {
        // execute_script src/main/java/script.txt
        String commandName;
        invoker.setCalledByScript(true);
        File script = new File(fileName);
        try (BufferedReader reader = new BufferedReader(new FileReader(script))) {
            List<String> linesOfScript = new ArrayList<>();
            String line = reader.readLine();
            while (line != null) {
                linesOfScript.add(line);
                line = reader.readLine();
            }
            List<String> arguments = new ArrayList<>();

            while (linesOfScript.size() != 0) {
                String command = linesOfScript.get(0);
                linesOfScript.remove(command);
                commandName = command.split(" ")[0];

                if (Objects.equals(commandName, "exit")) {
                    throw new NullPointerException();
                } else if (complexCommandNames.contains(commandName)) {

                    for (int i = 0; i < 7; ++i) {
                        arguments.add(linesOfScript.get(0));
                        linesOfScript.remove(0);
                    }
                    invoker.setListOfArgumentsForBuildingViaScript(arguments);
                }
                invoker.getRequestFromUser(command);
            }
        } catch (IOException e) {
            System.out.println("Error!");
            invoker.setCalledByScript(false);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Error while building vehicle. Rewrite your script");
            invoker.setCalledByScript(false);
        }
    }
}