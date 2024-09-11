package handlers;

import data.Vehicle;
import exceptions.BuildingInterruptionException;
import exceptions.ScriptBuildingException;
import generation.VehicleBuilder;
import output.TextReceiver;
import packets.CommandDescriptionPacket;
import packets.PacketWrapper;
import validation.CommandValidator;
import validation.ValidationReport;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserInputHandler {
    private final BufferedReader reader;
    private final TextReceiver receiver = new TextReceiver();
    private final CommandValidator validator = new CommandValidator();
    private final PacketWrapper wrapper = new PacketWrapper();
    private final VehicleBuilder builder = new VehicleBuilder();

    public UserInputHandler() {
        reader = new BufferedReader(new InputStreamReader(System.in));
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
                return wrapper.wrapCDP("exit");
            }

            message = message.trim();
            if (message.length() != 0) {
                // validation process
                ValidationReport validationReport = validator.checkCommand(message);
                switch (validationReport) {
                    case OK -> {
                        String[] command = validator.sliceCommand(message);
                        if (Objects.equals(command[1], "")) {
                            return wrapper.wrapCDP(command[0]);
                        }
                        return wrapper.wrapCDP(command[0], command[1]);
                    }
                    case OK_CALLING_BUILDER -> {
                        try {
                            Vehicle vehicle = builder.buildStepByStep();
                            String[] command = validator.sliceCommand(message);
                            if (Objects.equals(command[1], "")) {
                                return wrapper.wrapCDP(command[0], vehicle);
                            }
                            return wrapper.wrapCDP(command[0], command[1], vehicle);
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

    private List<CommandDescriptionPacket> handleScript(String userInput) {
        String fileName = validator.sliceCommand(userInput)[1];
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            List<CommandDescriptionPacket> packetList = new ArrayList<>();
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
                        packetList.add(wrapper.wrapCDP(command[0], command[1], vehicle));
                    } else {
                        packetList.add(wrapper.wrapCDP(command[0], vehicle));
                    }

                } else if (Objects.equals(command[0], "execute_script")) {
                    packetList.addAll(handleScript(scriptLine));
                } else if (!Objects.equals(command[1], "")) {
                    packetList.add(wrapper.wrapCDP(command[0], command[1]));
                } else {
                    packetList.add(wrapper.wrapCDP(command[0]));
                }
            }
            return packetList;
        } catch (ScriptBuildingException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
