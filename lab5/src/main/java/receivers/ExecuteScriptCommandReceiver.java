package receivers;

import commands.UpdateElementCommand;
import exceptions.NoArgumentException;
import exceptions.RecursionException;
import exceptions.ScriptBuildingException;
import exceptions.UnreadableFileException;
import user.Invoker;

import java.io.*;
import java.nio.file.Files;
import java.util.*;

/**
 * Receiver class
 * Stores realization for 'execute_script script' command
 * Uses BuilderCommandReceiver to tweak building commands' realization
 */
public class ExecuteScriptCommandReceiver {

    private final CollectionModifyingCommandReceiver receiver;

    private final ScriptBuildingReceiver scriptBuildingReceiver;

    private final TextReceiver textReceiver;

    private final Invoker invoker;

    private final List<String> complexCommandNames = new ArrayList<>();

    private final Set<String> setOfScriptPaths;
    private String commandName;
    private String commandArgument;
    private final List<String> arguments = new ArrayList<>();

    /**
     * Gets collection modifying receiver that is used for setting pre-built element<br>
     * Gets script building receiver for building elements using script data<br>
     * Gets invoker for altering the way command processes
     * @param receiver commands that use building
     * @param scriptBuildingReceiver builds elements
     * @param invoker calls commands
     */
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
        try {
            clearCommandParameters();
            File script = createScriptFile(fileName);
            checkIfScriptExistsElseThrowError(script);
            checkIfScriptReadableElseThrowError(script);
            addScriptFileToSet(script);
            textReceiver.print("Entering script " + script.getPath() + " ...");
            readScriptAndExecuteItsCommandsElseThrowError(script);
            return "Script " + fileName + " finished its execution";
        } catch (FileNotFoundException fileNotFoundException) {
            return "Script " + fileName + " was not found";
        } catch (UnreadableFileException unreadableFileException) {
            return "Script " + fileName + " cannot be read. Access denied";
        }
    }

    private void checkIfScriptExistsElseThrowError(File file) throws FileNotFoundException {
        if (!Files.exists(file.toPath())) {
            throw new FileNotFoundException();
        }
    }

    private void checkIfScriptReadableElseThrowError(File file) throws UnreadableFileException {
        if (!Files.isReadable(file.toPath())) {
            throw new UnreadableFileException();
        }
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
            textReceiver.print("File either not found or unreadable");
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
            buildVehicleAndReturnNewScriptLinesElseThrowError(scriptLines);
        }
    }

    private void buildVehicleAndReturnNewScriptLinesElseThrowError(List<String> scriptLines) throws ScriptBuildingException {
        try {
            for (int i = 0; i < 7; ++i) {
                arguments.add(scriptLines.get(0));
                scriptLines.remove(0);
            }
            buildVehicle();
        } catch (IndexOutOfBoundsException e) {
            textReceiver.print("Not enough arguments to build an object. Rewrite your script");
            throw new ScriptBuildingException();
        }
    }

    private void buildVehicle() throws ScriptBuildingException {
        receiver.setCurrentVehicle(scriptBuildingReceiver.buildOrThrowError(arguments));
    }

    private boolean checkUpdateCommand() {
        return Objects.equals(commandName, "update");
    }

    private boolean checkIfValidID() {
        UpdateElementCommand command = (UpdateElementCommand) invoker.getCommandHashMap().get("update");
        return command.checkIfValidID(commandArgument);
    }

    private void callCommandAndPrintReport() {
        try {
            if (checkUpdateCommand()) {
                if (checkIfValidID()) {
                    textReceiver.print(invoker.getCommandHashMap().get(commandName).execute(commandArgument));
                }
            } else {
                textReceiver.print(invoker.getCommandHashMap().get(commandName).execute(commandArgument));
            }
        } catch (NullPointerException e) {
            textReceiver.print("Command " + commandName + " does not exist");
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
