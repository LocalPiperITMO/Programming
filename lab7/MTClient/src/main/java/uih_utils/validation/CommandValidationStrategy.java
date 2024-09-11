package uih_utils.validation;

@FunctionalInterface
public interface CommandValidationStrategy {
    ValidationReport validate(String commandName, String commandArgument);
}
