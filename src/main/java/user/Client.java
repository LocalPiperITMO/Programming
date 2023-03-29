package user;

import collection.CollectionStorage;
import receivers.TextReceiver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Client class
 * Enables connection to Invoker
 * User can leave the program by typing 'exit' into the console
 */
public class Client {
    /**
     * Stores Invoker for communication user and the program
     */
    private final Invoker invoker;
    /**
     * Stores TextReceiver that gives feedback to user
     */
    private final TextReceiver textReceiver;
    public Client(CollectionStorage storage) {
        this.invoker = new Invoker(storage);
        this.textReceiver = new TextReceiver();
    }

    /**
     * Runs the user-console communication
     */
    public void runningMode() throws IOException {
        BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
        // app is running
        boolean isRunning = true;
        while (isRunning) {
            textReceiver.print("""
                    Enter command:
                    NOTE: if the command has additional parameters, input in this exact pattern:
                    [command_name] {parameter}""");
            String request = userInput.readLine();
            if (request == null || request.equalsIgnoreCase("exit")) {
                textReceiver.print("Leaving the program");
                isRunning = false;
            } else {
                try {
                    invoker.readUserRequest(request.trim());
                } catch (NullPointerException e) {
                    textReceiver.print("Leaving the program");
                    isRunning = false;
                }
            }
        }
    }
}
