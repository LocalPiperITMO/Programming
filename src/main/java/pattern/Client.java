package pattern;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Client {
    Invoker invoker;

    public Client(Receiver receiver) {
        this.invoker = new Invoker(receiver);
    }

    public void runningMode() throws IOException {
        BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
        boolean isRunning = true;
        while (isRunning) {
            System.out.println("""
                    Enter command:
                    NOTE: if the command has additional parameters, input in this exact pattern:
                    [command_name] {parameter1,parameter2,parameter3...}""");
            String request = userInput.readLine();
            if (request == null || request.equalsIgnoreCase("exit")) {
                System.out.println("Leaving the program");
                isRunning = false;
            } else {
                invoker.getRequestFromUser(request.strip());
            }
        }
    }
}
