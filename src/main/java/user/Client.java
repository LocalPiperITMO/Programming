package user;

import collection.CollectionStorage;
import receivers.TextReceiver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Client {
    private final Invoker invoker;
    private final TextReceiver textReceiver;

    /**
     * Client class
     * Enables communication between user and console.
     *
     * @param storage used for storing the collection
     */
    public Client(CollectionStorage storage) {
        this.invoker = new Invoker(storage);
        this.textReceiver = new TextReceiver();
    }

    /**
     * Runs the user-console communication
     *
     * @throws IOException if unexpected error occurs
     */
    public void runningMode() throws IOException {
        BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
        // app is running
        boolean isRunning = true;
        while (isRunning) {
            textReceiver.printReport("""
                    Enter command:
                    NOTE: if the command has additional parameters, input in this exact pattern:
                    [command_name] {parameter}""");
            String request = userInput.readLine();
            if (request == null || request.equalsIgnoreCase("exit")) {
                textReceiver.printReport("Leaving the program");
                isRunning = false;
            } else {
                try {
                    invoker.readUserRequest(request.trim());
                } catch (NullPointerException e) {
                    textReceiver.printReport("Leaving the program");
                    isRunning = false;
                }
            }
        }
    }
}
