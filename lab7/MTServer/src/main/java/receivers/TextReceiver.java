package receivers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Receiver class<br>
 * Outputs everything to the console (for now)
 */
public class TextReceiver {
    public void printToLog(String className, String message) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        System.out.printf("%s [%s]: %s\n", dtf.format(now), className, message);
    }
}
