import org.apache.commons.cli.*;

import java.io.IOException;
import java.net.BindException;

public class Server {

    public static void main(String[] args) throws IOException, ParseException {
        try {
            CommandLineParser commandLineParser = new DefaultParser();
            Options options = new Options();
            options.addOption("p", "port", true, "the port number");
            CommandLine cmd = commandLineParser.parse(options, args);
            int port = Integer.parseInt(cmd.getOptionValue("port", "1111"));
            if (port == 0 | (port / 1000) == 0 | port >= 65536) {
                System.out.println("Illegal port value. Port should be an integer in range [1000;65535]");
            } else {
                CommunicationHandler handler = new CommunicationHandler(port);
                handler.startListening();
            }
        } catch (NumberFormatException e) {
            System.out.println("Port should be an integer in range [1000;65535]");
        } catch (MissingArgumentException e) {
            System.out.println("When identifying \"port\" value, one should pass the argument (for example, -p 1111)");
        } catch (BindException e) {
            System.out.println("Seems like this port number is already in use. Try a different port");
        } catch (UnrecognizedOptionException e) {
            System.out.println("This option does not exist");
        }
    }
}
