package handlers;

import datatypes.Vehicle;
import exceptions.BuildingInterruptionException;
import exceptions.ScriptBuildingException;
import uih_utils.TextReceiver;
import uih_utils.VehicleBuilder;
import uih_utils.validation.CommandValidator;
import uih_utils.validation.ValidationReport;
import wrapping.AuthorizationPacket;
import wrapping.PacketWrapper;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static handlers.CommunicationHandler.CIP;

public class UserInputHandler {
    private final BufferedReader reader;
    private final TextReceiver receiver = new TextReceiver();
    private final PacketWrapper wrapper = new PacketWrapper();
    private final VehicleBuilder builder = new VehicleBuilder();
    private final CommandValidator validator;

    private final String username;
    private final String password;

    public UserInputHandler(AuthorizationPacket authorizationPacket) {

        reader = new BufferedReader(new InputStreamReader(System.in));
        validator = new CommandValidator(CIP);
        username = authorizationPacket.getUsername();
        password = authorizationPacket.getHashedPassword();
    }

    public void greetUser() {
        receiver.print("Enter commands in the following pattern: {command_name} [argument]" + "\nPrint \"help\" to see the full list of commands");
    }

    private boolean checkIfComplexCommand(String commandName) {
        return Objects.equals(commandName, "add") || Objects.equals(commandName, "add_if_max") || Objects.equals(commandName, "remove_greater") || Objects.equals(commandName, "update");
    }

    public Serializable handleInput() throws IOException {
        while (true) {

            System.out.print("> ");
            String message = reader.readLine();
            // check exit by force
            if (message == null) {
                return wrapper.wrapCDP(username, password, "exit");
            }

            message = message.trim();
            if (message.length() != 0) {
                // validation process
                ValidationReport validationReport = validator.checkCommand(message);
                switch (validationReport) {
                    case OK -> {
                        String[] command = validator.sliceCommand(message);
                        if (Objects.equals(command[1], "")) {
                            return wrapper.wrapCDP(username, password, command[0]);
                        }
                        return wrapper.wrapCDP(username, password, command[0], command[1]);
                    }
                    case OK_CALLING_BUILDER -> {
                        try {
                            Vehicle vehicle = builder.buildStepByStep();
                            String[] command = validator.sliceCommand(message);
                            if (Objects.equals(command[1], "")) {
                                return wrapper.wrapCDP(username, password, command[0], vehicle);
                            }
                            return wrapper.wrapCDP(username, password, command[0], command[1], vehicle);
                        } catch (BuildingInterruptionException e) {
                            receiver.print("Building interrupted");
                        }
                    }
                    case OK_SCRIPT -> {
                        return (Serializable) handleScript(message);
                    }
                    default -> receiver.print(validationReport.getMessage());
                }
            }
        }
    }


    private List<Serializable> handleScript(String userInput) {
        String fileName = validator.sliceCommand(userInput)[1];
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            List<Serializable> packetList = new ArrayList<>();
            String scriptLine;
            while ((scriptLine = bufferedReader.readLine()) != null) {
                Vehicle vehicle;
                String[] command = validator.sliceCommand(scriptLine);
                if (checkIfComplexCommand(command[0])) {
                    List<String> buildingArguments = new ArrayList<>();
                    for (int i = 0; i < 7 && (scriptLine = bufferedReader.readLine()) != null; i++) {
                        buildingArguments.add(scriptLine);
                    }
                    vehicle = new VehicleBuilder().buildViaScript(buildingArguments);
                    if (!Objects.equals(command[1], "")) {
                        packetList.add(wrapper.wrapCDP(username, password, command[0], command[1], vehicle));
                    } else {
                        packetList.add(wrapper.wrapCDP(username, password, command[0], vehicle));
                    }

                } else if (Objects.equals(command[0], "execute_script")) {
                    packetList.addAll(handleScript(scriptLine));
                } else if (!Objects.equals(command[1], "")) {
                    packetList.add(wrapper.wrapCDP(username, password, command[0], command[1]));
                } else {
                    packetList.add(wrapper.wrapCDP(username, password, command[0]));
                }
            }
            return packetList;
        } catch (ScriptBuildingException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
