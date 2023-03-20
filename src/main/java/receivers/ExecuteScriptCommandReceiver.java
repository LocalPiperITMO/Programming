package receivers;

import user.Invoker;
import exceptions.RecursionException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class ExecuteScriptCommandReceiver {
    private final BuilderCommandReceiver builderCommandReceiver;
    private final Invoker invoker;
    private final List<String> complexCommandNames = new ArrayList<>();

    private final Set<String> setOfScriptPaths;

    public ExecuteScriptCommandReceiver(BuilderCommandReceiver builderCommandReceiver, Invoker invoker) {
        this.builderCommandReceiver = builderCommandReceiver;
        this.invoker = invoker;
        this.setOfScriptPaths = new HashSet<>();
        this.complexCommandNames.add("add");
        this.complexCommandNames.add("add_if_max");
        this.complexCommandNames.add("remove_greater");
        this.complexCommandNames.add("update");
    }

    public void executeScript(String fileName) {
        String commandName;

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
            builderCommandReceiver.setScriptMode(false);
        } catch (IOException e) {
            System.out.println("Error!");
            builderCommandReceiver.setScriptMode(false);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Error while building vehicle. Rewrite your script");
            builderCommandReceiver.setScriptMode(false);
        } catch (RecursionException e) {
            System.out.println("Recursion avoided. Rewrite your script(s)");
            builderCommandReceiver.setScriptMode(false);
        }
    }
}
