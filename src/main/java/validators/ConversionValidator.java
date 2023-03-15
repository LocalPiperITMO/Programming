package validators;

/**
 * Validator class
 * Gives information about occurred errors to user
 * Only when building elements
 */
public class ConversionValidator {
    public void getInvalidNumberOfArgumentsExceptionMessage(int lineIndex) {
        System.out.println("Line " + lineIndex + " is missing one or several arguments." +
                "\nObject creation denied");
    }

    public void getLessOrEqualToZeroArgumentExceptionMessage(int lineIndex) {
        System.out.println("Line " + lineIndex + " has one or several numeric arguments that is/are less or equal to zero." +
                "\nObject creation denied");
    }

    public void getNonNumericArgumentExceptionMessage(int lineIndex) {
        System.out.println("Line " + lineIndex + " has one or several non-numeric arguments that must be numeric." +
                "\nObject creation denied");
    }

    public void getForbiddenNullArgumentExceptionMessage(int lineIndex) {
        System.out.println("Line " + lineIndex + " has one or several null values that are not allowed to be null." +
                "\nObject creation denied.");
    }

    public void getInvalidTypeArgumentExceptionMessage(int lineIndex) {
        System.out.println("Line " + lineIndex + " has invalid VehicleType/FuelType value." +
                "\nObject creation denied");
    }
}
