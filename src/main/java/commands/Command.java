package commands;

import datatype.Vehicle;
import exceptions.LessOrEqualToZeroException;
import exceptions.NoArgumentException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public interface Command {
    default boolean checkIfUserInputMatchesRequiredArgument(String userInput, boolean isArgumentRequired) {
        return userInput.trim().length() != 0 || !isArgumentRequired;
    }

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

    void execute(String arg) throws IOException, NoArgumentException;
}
