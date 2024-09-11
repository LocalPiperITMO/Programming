import exceptions.UserDisconnectException;
import handlers.CommunicationHandler;
import org.apache.commons.cli.*;
import uih_utils.PasswordGetter;

import java.security.NoSuchAlgorithmException;

public class Main {
    public static void main(String[] args) throws ParseException {
        try {
            CommandLineParser parser = new DefaultParser();
            Options options = new Options();
            options.addOption("p", "port", true, "the port number");
            options.addOption("u", "username", true, "username required to log in or register");
            options.addOption("r", "register", false, "flag that show that user needs to register");
            CommandLine cmd = parser.parse(options, args);
            int port = Integer.parseInt(cmd.getOptionValue("port", "1111"));
            String username = cmd.getOptionValue("username");
            if (username == null) {
                System.out.println("No name provided");
                throw new UserDisconnectException();
            } else if (username.length() > 50){
                System.out.println("Username is too long: should be no more than 50 characters");
                throw new UserDisconnectException();
            }
            PasswordGetter passwordGetter = new PasswordGetter(username);
            if (port == 0 | (port / 1000) == 0 | port >= 65536) {
                System.out.println("Illegal port value. Port should be an integer in range [1000;65535]");
                throw new UserDisconnectException();
            }
            boolean regFlag = cmd.hasOption("register");
            passwordGetter.requestPassword();
            String password = passwordGetter.getPassword();

            CommunicationHandler handler = new CommunicationHandler(port, username, password, regFlag);
            handler.startConnection();
        } catch (UnrecognizedOptionException e) {
            System.out.println("This option does not exist");
        } catch (OutOfMemoryError e) {
            System.out.println("You did not connect to the correct server. Please, check your port");
        } catch (MissingArgumentException e) {
            System.out.println("An argument is missing!");
        } catch (NumberFormatException e) {
            System.out.println("Illegal port value. Port should be an integer in range [1000;65535]");
        } catch (UserDisconnectException e) {
            System.out.println("Session is over");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}