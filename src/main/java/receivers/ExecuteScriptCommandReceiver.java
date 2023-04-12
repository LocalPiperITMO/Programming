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
    private String commandName;
    private String commandArgument;
    private final List<String> arguments = new ArrayList<>();

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
    public String execute(String fileName) {
        clearCommandParameters();
        File script = createScriptFile(fileName);
        addScriptFileToSet(script);
        readScriptAndExecuteItsCommandsElseThrowError(script);
        return "Script " + fileName + " executed successfully";
    }

    private void clearCommandParameters() {
        commandName = "";
        commandArgument = "";
    }

    private void setCommandParameters(String command) {
        commandName = command.split(" ", 2)[0];
        if (command.split(" ").length == 2) {
            commandArgument = command.split(" ", 2)[1];
        }
    }

    private void clearBuildingArguments() {
        arguments.clear();
    }

    private File createScriptFile(String fileName) {
        return new File(fileName);
    }

    private void addScriptFileToSet(File file) {
        setOfScriptPaths.add(file.getAbsolutePath());
    }

    private void removeScriptFileFromSet(File file) {
        setOfScriptPaths.remove(file.getAbsolutePath());
    }

    private void readScriptAndExecuteItsCommandsElseThrowError(File script) {
        try (BufferedReader reader = new BufferedReader(new FileReader(script))) {
            readAndExecuteScriptLines(getScriptLines(reader));
            removeScriptFileFromSet(script);
        } catch (IOException e) {
            textReceiver.print("Incorrect path to file");
        }
    }

    private List<String> getScriptLines(BufferedReader reader) throws IOException {
        List<String> linesOfScript = new ArrayList<>();
        String line = reader.readLine();
        while (line != null) {
            linesOfScript.add(line);
            line = reader.readLine();
        }
        return linesOfScript;
    }

    private void checkExit() {
        if (Objects.equals(commandName, "exit")) {
            throw new NullPointerException();
        }
    }

    private void checkScriptCall() throws RecursionException {
        if (Objects.equals(commandName, "execute_script")) {
            File newScript = createScriptFile(commandArgument);
            checkRecursion(newScript);
            addScriptFileToSet(newScript);
        }
    }

    private void checkRecursion(File newScript) throws RecursionException {
        if (setOfScriptPaths.contains(newScript.getAbsolutePath())) {
            removeScriptFileFromSet(newScript);
            throw new RecursionException();
        }
    }

    private void checkComplexCommandAndReturnNewScriptLines(List<String> scriptLines) throws ScriptBuildingException {
        if (complexCommandNames.contains(commandName)) {
            buildVehicleAndReturnNewScriptLines(scriptLines);
        }
    }

    private void buildVehicleAndReturnNewScriptLines(List<String> scriptLines) throws ScriptBuildingException {
        for (int i = 0; i < 7; ++i) {
            arguments.add(scriptLines.get(0));
            scriptLines.remove(0);
        }
        buildVehicle();
    }

    private void buildVehicle() throws ScriptBuildingException {
        receiver.setCurrentVehicle(scriptBuildingReceiver.buildOrThrowError(arguments));
    }

    private void callCommandAndPrintReport() {
        try {
            textReceiver.print(invoker.getCommandHashMap().get(commandName).execute(commandArgument));
        } catch (NoArgumentException e) {
            textReceiver.print("Command " + commandName + " requires an argument: none were given");
        } catch (IOException e) {
            textReceiver.print("Something went wrong");
        }
    }

    private void readAndExecuteScriptLines(List<String> scriptLines) {
        while (scriptLines.size() != 0) {
            try {
                setCommandParameters(scriptLines.get(0));
                scriptLines.remove(scriptLines.get(0));
                clearBuildingArguments();
                checkExit();
                checkScriptCall();
                checkComplexCommandAndReturnNewScriptLines(scriptLines);
                callCommandAndPrintReport();
            } catch (RecursionException e) {
                textReceiver.print("Recursion avoided. Rewrite your script(s)");
            } catch (ScriptBuildingException e) {
                textReceiver.print("Error while building vehicle. Rewrite your script");
            }
        }
    }
}
