package validation;

@FunctionalInterface
public interface CommandValidationStrategy {
    ValidationReport validate(String commandName, String commandArgument);
}
