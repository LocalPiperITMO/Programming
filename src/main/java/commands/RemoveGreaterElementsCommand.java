package commands;

import datatype.Vehicle;
import exceptions.LessOrEqualToZeroException;
import exceptions.NoArgumentException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class RemoveGreaterElementsCommand implements Command {
    private final Receiver receiver;

    public RemoveGreaterElementsCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    public void execute(String arg) throws IOException {
        Vehicle vehicle = new Vehicle();
        vehicle.setId(receiver.idGenerator().generateRandomID());
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        boolean isAllowedToProceed;
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
            } catch (NumberFormatException e) {
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

        receiver.dataSet().removeIf(vehicleToCompare -> vehicleToCompare.getSum() > vehicle.getSum());
        System.out.println("Done");
    }
}