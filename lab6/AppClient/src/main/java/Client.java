import exceptions.UserDisconnectException;
import handlers.ConnectionHandler;
import org.apache.commons.cli.*;

import java.io.IOException;

public class Client {

    public static void main(String[] args) throws ParseException {
        try {
            CommandLineParser parser = new DefaultParser();
            Options options = new Options();
            options.addOption("p", "port", true, "the port number");
            CommandLine cmd = parser.parse(options, args);
            int port = Integer.parseInt(cmd.getOptionValue("port", "1111"));
            if (port == 0 | (port / 1000) == 0 | port >= 65536) {
                System.out.println("Illegal port value. Port should be an integer in range [1000;65535]");
            } else {
                ConnectionHandler handler = new ConnectionHandler();
                handler.startConnection(port);
            }
        } catch (UnrecognizedOptionException e) {
            System.out.println("This option does not exist");
        } catch (OutOfMemoryError e) {
            System.out.println("You did not connect to the correct server. Please, check your port");
        } catch (IOException e) {
            System.out.println("Server is down. Try connecting later");
        } catch (UserDisconnectException e) {
            System.out.println("Session is over");
        } catch (MissingArgumentException e) {
            System.out.println("When identifying \"port\" value, one should pass the argument (for example, -p 1111)");
        } catch (NumberFormatException e) {
            System.out.println("Illegal port value. Port should be an integer in range [1000;65535]");
        }
    }
}

