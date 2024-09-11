package validation;

import org.apache.commons.lang.math.NumberUtils;

import java.util.HashMap;

public class ArgumentValidationStrategy implements CommandValidationStrategy {
    private final HashMap<String, String[]> commandLibrary;

    public ArgumentValidationStrategy(HashMap<String, String[]> commandLibrary) {
        this.commandLibrary = commandLibrary;
    }

    @Override
    public ValidationReport validate(String commandName, String commandArgument) {
        if (commandArgument.isEmpty()) {
            if (commandLibrary.get(commandName)[1].isEmpty()) {
                return getCodeBasedOnBuildingRequired(commandName);
            } else {
                return ValidationReport.NO_ARGUMENT_PASSED;
            }
        } else {
            if (commandLibrary.get(commandName)[1].isEmpty()) {
                return ValidationReport.REDUNDANT_ARGUMENT;
            }
            String argType = commandLibrary.get(commandName)[1];
            switch (argType) {
                case "Integer" -> {
                    if (NumberUtils.isNumber(commandArgument)) {
                        int parsed = Integer.parseInt(commandArgument);
                        if (isInRange(parsed)) {
                            return getCodeBasedOnBuildingRequired(commandName);
                        }
                        return ValidationReport.INTEGER_ARGUMENT_OUT_OF_BOUNDS;
                    }
                    return ValidationReport.ILLEGAL_ARGUMENT_TYPE;
                }
                case "Long" -> {
                    if (NumberUtils.isNumber(commandArgument)) {
                        long parsed = Long.parseLong(commandArgument);
                        if (isInRange(parsed)) {
                            return getCodeBasedOnBuildingRequired(commandName);
                        }
                        return ValidationReport.LONG_ARGUMENT_OUT_OF_BOUNDS;
                    }
                    return ValidationReport.ILLEGAL_ARGUMENT_TYPE;
                }
                default -> {
                    return ValidationReport.UNKNOWN_ERROR;
                }
            }
        }
    }

    private boolean isInRange(int value) {
        return value > 0 && value < 1000000;
    }

    private boolean isInRange(long value) {
        return value > 0 && value < 4000000000L;
    }

    private ValidationReport getCodeBasedOnBuildingRequired(String commandName) {
        if (commandLibrary.get(commandName)[0].isEmpty()) {
            return ValidationReport.OK;
        } else {
            return ValidationReport.OK_CALLING_BUILDER;
        }
    }
}

