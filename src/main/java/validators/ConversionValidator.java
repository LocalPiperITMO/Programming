package validators;

public class ConversionValidator {
    public void invalidNumberOfArguments(int lineIndex) {
        System.out.println("Line " + lineIndex + " is missing one or several arguments." +
                "\nObject creation denied");
    }

    public void lessOrEqualToZeroArgument(int lineIndex) {
        System.out.println("Line " + lineIndex + " has one or several numeric arguments that is/are less or equal to zero." +
                "\nObject creation denied");
    }

    public void nonNumericArgument(int lineIndex) {
        System.out.println("Line " + lineIndex + " has one or several non-numeric arguments that must be numeric." +
                "\nObject creation denied");
    }

    public void forbiddenNullArgument(int lineIndex) {
        System.out.println("Line " + lineIndex + " has one or several null values that are not allowed to be null." +
                "\nObject creation denied.");
    }

    public void invalidTypeArgument(int lineIndex) {
        System.out.println("Line " + lineIndex + " has invalid VehicleType/FuelType value." +
                "\nObject creation denied");
    }
}
