package commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Client {
    private final Invoker invoker;

    /**
     * Client class
     * Enables communication between user and console.
     *
     * @param receiver used for storing the collection
     */
    public Client(Receiver receiver) {
        this.invoker = new Invoker(receiver);
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
            System.out.println("""
                    Enter command:
                    NOTE: if the command has additional parameters, input in this exact pattern:
                    [command_name] {parameter}""");
            String request = userInput.readLine();
            if (request == null || request.equalsIgnoreCase("exit")) {
                System.out.println("Leaving the program");
                isRunning = false;
            } else {
                try {
                    invoker.getRequestFromUser(request.strip());
                } catch (NullPointerException e) {
                    System.out.println("Leaving the program");
                    isRunning = false;
                }
            }
        }
    }
}
