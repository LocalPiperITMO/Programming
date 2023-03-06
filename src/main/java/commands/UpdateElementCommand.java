package commands;

import datatype.Vehicle;
import exceptions.LessOrEqualToZeroException;
import exceptions.NoArgumentException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class UpdateElementCommand implements Command {
    private final Receiver receiver;

    public UpdateElementCommand(Receiver receiver) {
        this.receiver = receiver;
    }


    public void execute(String argument) throws IOException, NoArgumentException {
        if (checkIfUserInputMatchesRequiredArgument(argument, true)) {
            try {
                int id = Integer.parseInt(argument);
                boolean isFound = false;
                Vehicle vehicle = new Vehicle();
                for (Vehicle vehicleToFind : receiver.dataSet()) {
                    if (vehicleToFind.getId() == id) {
                        vehicle = vehicleToFind;
                        isFound = true;
                        break;
                    }
                }
                if (!isFound) {
                    System.out.println("Element with given ID does not exist");
                } else {
                    boolean isAllowedToProceed;
                    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                    do {
                        isAllowedToProceed = true;
                        System.out.println("Input name (must not be empty):");
                        try {
                            vehicle.setName(reader.readLine());
                        } catch (NoArgumentException e) {
                            System.out.println("Wrong input! Try again");
                            isAllowedToProceed = false;
                        }
                    } while (!isAllowedToProceed);
                    do {
                        isAllowedToProceed = true;
                        System.out.println("Input x (numerical, must not be empty):");
                        try {
                            vehicle.getCoordinates().setX(reader.readLine());
                        } catch (NumberFormatException | NullPointerException e) {
                            System.out.println("Wrong input! Try again");
                            isAllowedToProceed = false;
                        }
                    } while (!isAllowedToProceed);
                    do {
                        isAllowedToProceed = true;
                        System.out.println("Input y (numerical, must not be empty):");
                        try {
                            vehicle.getCoordinates().setY(reader.readLine());
                        } catch (NumberFormatException e) {
                            System.out.println("Wrong input! Try again");
                            isAllowedToProceed = false;
                        }
                    } while (!isAllowedToProceed);
                    do {
                        isAllowedToProceed = true;
                        System.out.println("Input enginePower (numerical, greater than 0, must not be null):");
                        try {
                            vehicle.setEnginePower(reader.readLine());
                        } catch (LessOrEqualToZeroException | NumberFormatException e) {
                            System.out.println("Wrong input! Try again");
                            isAllowedToProceed = false;
                        }
                    } while (!isAllowedToProceed);
                    do {
                        isAllowedToProceed = true;
                        System.out.println("Input fuelConsumption (numerical, greater than 0, must not be null):");
                        try {
                            vehicle.setFuelConsumption(reader.readLine());
                        } catch (LessOrEqualToZeroException | NumberFormatException e) {
                            System.out.println("Wrong input! Try again");
                            isAllowedToProceed = false;
                        }
                    } while (!isAllowedToProceed);
                    do {
                        isAllowedToProceed = true;
                        System.out.println("Input vehicleType (leave blank if null, one of those: PLANE, HELICOPTER, BOAT, BICYCLE, CHOPPER):");
                        try {
                            vehicle.setType(reader.readLine());
                        } catch (IllegalArgumentException e) {
                            System.out.println("Wrong input! Try again");
                            isAllowedToProceed = false;
                        }
                    } while (!isAllowedToProceed);
                    do {
                        isAllowedToProceed = true;
                        System.out.println("Input fuelType (leave blank if null, one of those: KEROSENE, MANPOWER, NUCLEAR, PLASMA, ANTIMATTER):");
                        try {
                            vehicle.setFuelType(reader.readLine());
                        } catch (IllegalArgumentException e) {
                            System.out.println("Wrong input! Try again");
                            isAllowedToProceed = false;
                        }
                    } while (!isAllowedToProceed);
                }
            } catch (NumberFormatException e) {
                System.out.println("ID must be integer, not string");
            }

        } else {
            throw new NoArgumentException();
        }
    }
}