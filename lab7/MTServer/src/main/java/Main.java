import clienthandling.ClientHandler;
import exceptions.DeclineDatabaseConnectionException;
import org.apache.commons.cli.*;

public class Main {
    public static void main(String[] args) throws ParseException {
        // parsing command line
        ClientHandler handler;
        try {
            CommandLineParser commandLineParser = new DefaultParser();
            Options options = new Options();
            options.addOption("p", "port", true, "the port number");
            options.addOption("cp", "config_path", true, "path to database info");
            options.addOption("clp", "command_list_path", true, "path to list of commands");
            CommandLine cmd = commandLineParser.parse(options, args);
            int port = Integer.parseInt(cmd.getOptionValue("port", "1111"));
            String configPath = cmd.getOptionValue("config_path", "src/main/java/conf");
            String commandListPath = cmd.getOptionValue("command_list_path", "src/main/java/command_list");
            if (port == 0 | (port / 1024) == 0 | port >= 65536) {
                System.out.println("Illegal port value. Port should be an integer in range [1000;65535]");
            } else {
                //if all good, sending args to handler for opening
                handler = new ClientHandler(port, configPath, commandListPath);
                handler.start();
            }
        } catch (NumberFormatException e) {
            System.out.println("Port should be an integer in range [1000;65535]");
        } catch (MissingArgumentException e) {
            System.out.println("When identifying \"port\" value, one should pass the argument (for example, -p 1111)");
        } catch (UnrecognizedOptionException e) {
            System.out.println("This option does not exist");
        } catch (DeclineDatabaseConnectionException e) {
            System.out.println("Can't connect to database");
        }
    }
}