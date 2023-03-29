package receivers;

/**
 * Receiver class
 * Outputs everything to the console (for now)
 */
public class TextReceiver {
    /**
     * Takes a report and prints it to the console
     * @param report command execution report (can sometimes be validator messages)
     */
    public void print(String report) {
        System.out.println(report);
    }
}
