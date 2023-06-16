package validation;

public enum ValidationReport {
    OK("OK"),
    NO_ARGUMENT_PASSED("No argument passed"),
    COMMAND_DOES_NOT_EXIST("Command does not exist"),
    REDUNDANT_ARGUMENT("Redundant argument"),
    ILLEGAL_ARGUMENT_TYPE("Illegal argument type"),
    INTEGER_ARGUMENT_OUT_OF_BOUNDS("Integer argument out of bounds: should be in range (0,1000000)"),
    LONG_ARGUMENT_OUT_OF_BOUNDS("Long argument out of bounds: should be in range (0,4000000000)"),
    FILE_NOT_FOUND("File not found"),
    ERROR_WHILE_BUILDING_VEHICLE("Error while building Vehicle. Rewrite your script"),
    RECURSION_AVOIDED("Recursion avoided. Rewrite your script"),
    OK_CALLING_BUILDER("OK, calling Builder"),
    SCRIPT_DETECTED("Script detected"),
    OK_SCRIPT("Script is valid"),
    SCRIPT_CANNOT_BE_READ("Script cannot be read. Access denied"),
    SCRIPT_NOT_FOUND("Script not found"),
    NOT_ENOUGH_ARGUMENTS("Script building failed: not enough arguments"),
    UNKNOWN_ERROR("That wasn't supposed to happen");


    private final String message;

    ValidationReport(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}