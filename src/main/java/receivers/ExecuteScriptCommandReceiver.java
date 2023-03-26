package receivers;

import exceptions.RecursionException;
import user.Invoker;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class ExecuteScriptCommandReceiver {
    private final BuilderCommandReceiver builderCommandReceiver;
    private final TextReceiver textReceiver;
    private final Invoker invoker;
    private final List<String> complexCommandNames = new ArrayList<>();
    private final Set<String> setOfScriptPaths;

    public ExecuteScriptCommandReceiver(BuilderCommandReceiver builderCommandReceiver, Invoker invoker) {
        this.builderCommandReceiver = builderCommandReceiver;
        this.textReceiver = new TextReceiver();
        this.invoker = invoker;
        this.setOfScriptPaths = new HashSet<>();
        this.complexCommandNames.add("add");
        this.complexCommandNames.add("add_if_max");
        this.complexCommandNames.add("remove_greater");
        this.complexCommandNames.add("update");
    }

    public String executeScript(String fileName) {
        String commandName;
        // execute_script src/main/java/script.txt
        File script = new File(fileName);
        setOfScriptPaths.add(script.getAbsolutePath());
        try (BufferedReader reader = new BufferedReader(new FileReader(script))) {
            List<String> linesOfScript = new ArrayList<>();
            String line = reader.readLine();
            while (line != null) {
                linesOfScript.add(line);
                line = reader.readLine();
            }
            List<String> arguments = new ArrayList<>();

            while (linesOfScript.size() != 0) {
                builderCommandReceiver.setScriptMode(true);
                String command = linesOfScript.get(0);
                linesOfScript.remove(command);
                commandName = command.split(" ")[0];
                arguments.clear();

                if (Objects.equals(commandName, "exit")) {
                    throw new NullPointerException();
                } else if (Objects.equals(commandName, "execute_script")) {
                    File nextScript = new File(command.split(" ")[1]);
                    if (setOfScriptPaths.contains(nextScript.getAbsolutePath())) {
                        setOfScriptPaths.remove(script.getAbsolutePath());
                        throw new RecursionException();
                    }
                } else if (complexCommandNames.contains(commandName)) {
                    for (int i = 0; i < 7; ++i) {
                        arguments.add(linesOfScript.get(0));
                        linesOfScript.remove(0);
                    }
                    builderCommandReceiver.setArguments(arguments);
                }
                invoker.getRequestFromUser(command);
            }
            setOfScriptPaths.remove(script.getAbsolutePath());
            builderCommandReceiver.setScriptMode(false);
        } catch (IOException e) {
            textReceiver.printReport("Error!");
            builderCommandReceiver.setScriptMode(false);
        } catch (IndexOutOfBoundsException e) {
            textReceiver.printReport("Error while building vehicle. Rewrite your script");
            builderCommandReceiver.setScriptMode(false);
        } catch (RecursionException e) {
            textReceiver.printReport("Recursion avoided. Rewrite your script(s)");
            builderCommandReceiver.setScriptMode(false);
        }
        return "Script " + fileName + " executed successfully";
    }
}
