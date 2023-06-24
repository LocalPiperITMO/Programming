package uih_utils.validation;

import exceptions.ScriptBuildingException;
import uih_utils.VehicleBuilder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

import static handlers.CommunicationHandler.CIP;

public class ScriptValidationStrategy implements CommandValidationStrategy {
    private static final HashSet<String> openedScripts = new HashSet<>();

    public ScriptValidationStrategy() {
    }

    @Override
    public ValidationReport validate(String commandName, String commandArgument) {
        // Check if file exists and is readable
        File scriptFile = new File(commandArgument);
        if (!scriptFile.exists()) {
            return ValidationReport.SCRIPT_NOT_FOUND;
        }
        if (!scriptFile.canRead()) {
            return ValidationReport.SCRIPT_CANNOT_BE_READ;
        }

        // Add file to list of opened scripts
        openedScripts.add(scriptFile.getAbsolutePath());

        // Read script line by line and validate each command
        try (BufferedReader reader = new BufferedReader(new FileReader(scriptFile))) {
            String scriptLine;
            CommandValidator validator = new CommandValidator(CIP);
            ValidationReport report;
            int lineNum = 1;
            while ((scriptLine = reader.readLine()) != null) {
                String[] command = validator.sliceCommand(scriptLine);
                if (Objects.equals(command[0], "execute_script")) {
                    report = handleExecuteScriptCommand(command[1]);
                } else {
                    report = validateCommand(scriptLine, validator, reader, lineNum);
                }
                if (!report.equals(ValidationReport.OK)) {
                    System.out.println("Error found in the line " + lineNum + " of the script " + commandArgument + ":");
                    return report;
                }
                ++lineNum;
            }

        } catch (IOException e) {
            System.out.println("Somehow, an error occurred");
        }

        // Remove file from list of opened scripts
        openedScripts.remove(scriptFile.getAbsolutePath());
        return ValidationReport.OK_SCRIPT;
    }

    private ValidationReport handleExecuteScriptCommand(String scriptPath) {
        File newScript = new File(scriptPath);
        if (openedScripts.contains(newScript.getAbsolutePath())) {
            return ValidationReport.RECURSION_AVOIDED;
        } else {
            ValidationReport report = validate("execute_script", scriptPath);
            return report.equals(ValidationReport.OK_SCRIPT) ? ValidationReport.OK : report;
        }
    }

    private ValidationReport validateCommand(String scriptLine, CommandValidator validator, BufferedReader reader, int lineNum) throws IOException {
        ValidationReport report = validator.checkCommand(scriptLine);
        if (report.equals(ValidationReport.OK_CALLING_BUILDER)) {
            List<String> buildingArguments = new ArrayList<>();
            for (int i = 0; i < 7 && (scriptLine = reader.readLine()) != null; i++) {
                buildingArguments.add(scriptLine);
                ++lineNum;
            }
            if (buildingArguments.size() != 7) {
                report = ValidationReport.NOT_ENOUGH_ARGUMENTS;
            } else {
                try {
                    new VehicleBuilder().buildViaScript(buildingArguments);
                    report = ValidationReport.OK;
                } catch (ScriptBuildingException e) {
                    report = ValidationReport.ERROR_WHILE_BUILDING_VEHICLE;
                }
            }
        }
        return report;
    }
}
