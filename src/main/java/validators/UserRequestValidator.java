package validators;

public class UserRequestValidator {
    public void emptyRequest() {
        System.out.println("Empty request. Try again");
    }

    public void invalidCommandRequest(String invalidCommandName) {
        System.out.println("There is no command named \"" + invalidCommandName + "\". Try again");
    }
}
