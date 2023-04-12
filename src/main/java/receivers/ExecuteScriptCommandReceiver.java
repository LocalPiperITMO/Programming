package receivers;

import exceptions.NoArgumentException;
import exceptions.RecursionException;
import exceptions.ScriptBuildingException;
import user.Invoker;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Receiver class
 * Stores realization for 'execute_script script' command
 * Uses BuilderCommandReceiver to tweak building commands' realization
 */
public class ExecuteScriptCommandReceiver {
    /**
     * Used for tweaking realization
     */
    private final CollectionModifyingCommandReceiver receiver;
    /**
     * Used as an output stream
     */
    private final ScriptBuildingReceiver scriptBuildingReceiver;
    private final TextReceiver textReceiver;
    /**
     * Used for sending commands to corresponding receivers
     */
    private final Invoker invoker;
    /**
     * Stores names of building commands
     */
    private final List<String> complexCommandNames = new ArrayList<>();
    /**
     * Stores paths of opened scripts (used for solving recursion problems)
     */
    private final Set<String> setOfScriptPaths;

    public ExecuteScriptCommandReceiver(CollectionModifyingCommandReceiver receiver, ScriptBuildingReceiver scriptBuildingReceiver, Invoker invoker) {
        this.receiver = receiver;
        this.textReceiver = new TextReceiver();
        this.scriptBuildingReceiver = scriptBuildingReceiver;
        this.invoker = invoker;
        this.setOfScriptPaths = new HashSet<>();
        this.complexCommandNames.add("add");
        this.complexCommandNames.add("add_if_max");
        this.complexCommandNames.add("remove_greater");
        this.complexCommandNames.add("update");
    }

    /**
     * 'execute_script script' command realization
     *
     * @param fileName name of script
     * @return command execution report (sent to TextReceiver)
     */
    public String executeScript(String fileName) {
        String commandName = "";
        String commandArgument = "";
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
                String command = linesOfScript.get(0);
                linesOfScript.remove(command);
                commandName = command.split(" ", 2)[0];
                if (command.split(" ", 2).length == 2) {
                    commandArgument = command.split(" ", 2)[1];
                }
                arguments.clear();

                if (Objects.equals(commandName, "exit")) {
                    throw new NullPointerException();
                } else if (Objects.equals(commandName, "execute_script")) {
                    File nextScript = new File(commandArgument);
                    if (setOfScriptPaths.contains(nextScript.getAbsolutePath())) {
                        setOfScriptPaths.remove(script.getAbsolutePath());
                        throw new RecursionException();
                    }
                } else if (complexCommandNames.contains(commandName)) {
                    for (int i = 0; i < 7; ++i) {
                        arguments.add(linesOfScript.get(0));
                        linesOfScript.remove(0);
                    }
                    receiver.setCurrentVehicle(scriptBuildingReceiver.buildOrThrowError(arguments));
                }
                textReceiver.print(invoker.getCommandHashMap().get(commandName).execute(commandArgument));
            }
            setOfScriptPaths.remove(script.getAbsolutePath());
        } catch (IOException e) {
            return "Incorrect path to file";
        } catch (NoArgumentException e) {
            textReceiver.print("Command " + commandName + " requires an argument: none were given");
        } catch (ScriptBuildingException e) {
            textReceiver.print("Error while building vehicle. Rewrite your script");
        } catch (RecursionException e) {
            textReceiver.print("Recursion avoided. Rewrite your script(s)");


        }
        return "Script " + fileName + " executed successfully";
    }
}
