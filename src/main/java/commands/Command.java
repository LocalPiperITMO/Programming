package commands;

import datatype.Vehicle;
import exceptions.InvalidArgumentsWhileVehicleBuildingViaScriptException;
import exceptions.LessOrEqualToZeroException;
import exceptions.NoArgumentException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public interface Command {
    /**
     * Used for showing information.
     *
     * @return information about the command
     */
    String showInfo();

    /**
     * Checks if type of argument, given by user, matches the type of argument required
     *
     * @param userInput          argument, given by user
     * @param isArgumentRequired tells if command needs an argument to work
     * @return true if user-written argument matches the requirements of command, false otherwise
     */
    default boolean checkIfUserInputMatchesRequiredArgument(String userInput, boolean isArgumentRequired) {
        return userInput.trim().length() != 0 || !isArgumentRequired;
    }

    /**
     * Builds vehicle using real-time communication with user
     *
     * @param vehicleBase requires the base of element that will be built (object with generated ID)
     * @return built element
     * @throws IOException if unexpected error occurs
     */
    default Vehicle buildVehicleViaUserInput(Vehicle vehicleBase) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        boolean isAllowedToProceed;
        do {
            isAllowedToProceed = true;
            System.out.println("Input name (must not be empty):");
            try {
                vehicleBase.setName(reader.readLine());
            } catch (NoArgumentException e) {
                System.out.println("Wrong input! Try again");
                isAllowedToProceed = false;
            }
        } while (!isAllowedToProceed);
        do {
            isAllowedToProceed = true;
            System.out.println("Input x (numerical, must not be empty):");
            try {
                vehicleBase.getCoordinates().setX(reader.readLine());
            } catch (NumberFormatException e) {
                System.out.println("Wrong input! Try again");
                isAllowedToProceed = false;
            }
        } while (!isAllowedToProceed);
        do {
            isAllowedToProceed = true;
            System.out.println("Input y (numerical, must not be empty):");
            try {
                vehicleBase.getCoordinates().setY(reader.readLine());
            } catch (NumberFormatException e) {
                System.out.println("Wrong input! Try again");
                isAllowedToProceed = false;
            }
        } while (!isAllowedToProceed);
        do {
            isAllowedToProceed = true;
            System.out.println("Input enginePower (numerical, greater than 0, must not be null):");
            try {
                vehicleBase.setEnginePower(reader.readLine());
            } catch (LessOrEqualToZeroException | NumberFormatException e) {
                System.out.println("Wrong input! Try again");
                isAllowedToProceed = false;
            }
        } while (!isAllowedToProceed);
        do {
            isAllowedToProceed = true;
            System.out.println("Input fuelConsumption (numerical, greater than 0, must not be null):");
            try {
                vehicleBase.setFuelConsumption(reader.readLine());
            } catch (LessOrEqualToZeroException | NumberFormatException e) {
                System.out.println("Wrong input! Try again");
                isAllowedToProceed = false;
            }
        } while (!isAllowedToProceed);
        do {
            isAllowedToProceed = true;
            System.out.println("Input vehicleType (leave blank if null, one of those: PLANE, HELICOPTER, BOAT, BICYCLE, CHOPPER):");
            try {
                vehicleBase.setType(reader.readLine());
            } catch (IllegalArgumentException e) {
                System.out.println("Wrong input! Try again");
                isAllowedToProceed = false;
            }
        } while (!isAllowedToProceed);
        do {
            isAllowedToProceed = true;
            System.out.println("Input fuelType (leave blank if null, one of those: KEROSENE, MANPOWER, NUCLEAR, PLASMA, ANTIMATTER):");
            try {
                vehicleBase.setFuelType(reader.readLine());
            } catch (IllegalArgumentException e) {
                System.out.println("Wrong input! Try again");
                isAllowedToProceed = false;
            }
        } while (!isAllowedToProceed);
        return vehicleBase;
    }

    /**
     * Builds vehicle using arguments given in the script (used via "execute_script" command)
     *
     * @param arguments list of arguments given
     * @return built element
     * @throws InvalidArgumentsWhileVehicleBuildingViaScriptException if invalid arguments given
     */
    default Vehicle buildVehicleViaScript(List<String> arguments) throws InvalidArgumentsWhileVehicleBuildingViaScriptException {
        try {
            Vehicle vehicle = new Vehicle().setName(arguments.get(0));
            vehicle.getCoordinates().setX(arguments.get(1))
                    .setY(arguments.get(2));
            vehicle.setEnginePower(arguments.get(3))
                    .setFuelConsumption(arguments.get(4))
                    .setType(arguments.get(5))
                    .setFuelType(arguments.get(6));
            return vehicle;
        } catch (NoArgumentException | LessOrEqualToZeroException | IllegalArgumentException |
                 IndexOutOfBoundsException e) {
            throw new InvalidArgumentsWhileVehicleBuildingViaScriptException();
        }
    }

    /**
     * Executes command
     *
     * @param arg              command argument
     * @param isCalledByScript checks if command called from script
     * @throws IOException                                            if unexpected error occurs
     * @throws NoArgumentException                                    if command requires argument but none were given
     * @throws InvalidArgumentsWhileVehicleBuildingViaScriptException if invalid arguments given for building vehicle via script
     */
    void execute(String arg, boolean isCalledByScript) throws IOException, NoArgumentException, InvalidArgumentsWhileVehicleBuildingViaScriptException;
}
