package uih_utils;

import datatypes.Vehicle;
import exceptions.BuildingInterruptionException;
import exceptions.LessOrEqualToZeroException;
import exceptions.NoArgumentException;
import exceptions.ScriptBuildingException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;

public class VehicleBuilder {
    private final Vehicle vehicle;

    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    private void showMessageToUser(String message) {
        System.out.println(message);
    }

    /**
     * Creates base for new element
     */
    public VehicleBuilder() {
        this.vehicle = new Vehicle();
    }

    private void checkIfUserStops(String userInput) throws BuildingInterruptionException {
        if (Objects.equals(userInput, "/stop")) {
            throw new BuildingInterruptionException();
        }
    }

    private void requestNameUntilFits() throws BuildingInterruptionException {
        while (true) {
            try {
                showMessageToUser("Input name (number of characters: from 1 to 20, must not be empty):");
                String userInput = reader.readLine();
                checkIfUserStops(userInput);
                vehicle.setName(userInput);
                return;
            } catch (NoArgumentException noArgumentException) {
                showMessageToUser("Wrong input! Please, try again");
            } catch (IOException ioException) {
                showMessageToUser("Something went wrong, try again");
            }
        }
    }

    private void requestCoordinateXUntilFits() throws BuildingInterruptionException {
        while (true) {
            try {
                showMessageToUser("Input x (Float value, -2 000 000 <=x<=2 000 000, must not be empty):");
                String userInput = reader.readLine();
                checkIfUserStops(userInput);
                vehicle.getCoordinates().setX(userInput);
                return;
            } catch (NumberFormatException numberFormatException) {
                showMessageToUser("Wrong input! Please, try again");
            } catch (IOException ioException) {
                showMessageToUser("Something went wrong, try again");
            }
        }
    }

    private void requestCoordinateYUntilFits() throws BuildingInterruptionException {
        while (true) {
            try {
                showMessageToUser("Input y (Integer value, -2 000 000 000<=x<=2 000 000 000, must not be empty):");
                String userInput = reader.readLine();
                checkIfUserStops(userInput);
                vehicle.getCoordinates().setY(userInput);
                return;
            } catch (NumberFormatException numberFormatException) {
                showMessageToUser("Wrong input! Please, try again");
            } catch (IOException ioException) {
                showMessageToUser("Something went wrong, try again");
            }
        }
    }

    private void requestEnginePowerUntilFits() throws BuildingInterruptionException {
        while (true) {
            try {
                showMessageToUser("Input enginePower (Long value, 0<y<=4 000 000 000, must not be null):");
                String userInput = reader.readLine();
                checkIfUserStops(userInput);
                vehicle.setEnginePower(userInput);
                return;
            } catch (LessOrEqualToZeroException | NumberFormatException illegalArgumentException) {
                showMessageToUser("Wrong input! Please, try again");
            } catch (IOException ioException) {
                showMessageToUser("Something went wrong, try again");
            }
        }
    }

    private void requestFuelConsumptionUntilFits() throws BuildingInterruptionException {
        while (true) {
            try {
                showMessageToUser("Input fuelConsumption (Long value, 0<y<=4 000 000 000, must not be null):");
                String userInput = reader.readLine();
                checkIfUserStops(userInput);
                vehicle.setFuelConsumption(userInput);
                return;
            } catch (LessOrEqualToZeroException | NumberFormatException illegalArgumentException) {
                showMessageToUser("Wrong input! Please, try again");
            } catch (IOException ioException) {
                showMessageToUser("Something went wrong, try again");
            }
        }
    }

    private void requestVehicleTypeUntilFits() throws BuildingInterruptionException {
        while (true) {
            try {
                showMessageToUser("Input vehicleType (leave blank if null, one of those: PLANE, HELICOPTER, BOAT, BICYCLE, CHOPPER):");
                String userInput = reader.readLine();
                checkIfUserStops(userInput);
                vehicle.setType(userInput);
                return;
            } catch (IllegalArgumentException illegalArgumentException) {
                showMessageToUser("Wrong input! Please, try again");
            } catch (IOException ioException) {
                showMessageToUser("Something went wrong, try again");
            }
        }
    }

    private void requestFuelTypeUntilFits() throws BuildingInterruptionException {
        while (true) {
            try {
                showMessageToUser("Input fuelType (leave blank if null, one of those: KEROSENE, MANPOWER, NUCLEAR, PLASMA, ANTIMATTER):");
                String userInput = reader.readLine();
                checkIfUserStops(userInput);
                vehicle.setFuelType(userInput);
                return;
            } catch (IllegalArgumentException illegalArgumentException) {
                showMessageToUser("Wrong input! Please, try again");
            } catch (IOException ioException) {
                showMessageToUser("Something went wrong, try again");
            }
        }
    }

    public Vehicle buildStepByStep() throws BuildingInterruptionException {
        requestNameUntilFits();
        requestCoordinateXUntilFits();
        requestCoordinateYUntilFits();
        requestEnginePowerUntilFits();
        requestFuelConsumptionUntilFits();
        requestVehicleTypeUntilFits();
        requestFuelTypeUntilFits();
        return vehicle;
    }

    public Vehicle buildViaScript(List<String> data) throws ScriptBuildingException {
        try {
            Vehicle currentVehicle = new Vehicle();
            currentVehicle.setName(data.get(0))
                    .getCoordinates()
                    .setX(data.get(1))
                    .setY(data.get(2));
            currentVehicle.setEnginePower(data.get(3))
                    .setFuelConsumption(data.get(4))
                    .setType(data.get(5))
                    .setFuelType(data.get(6));
            return currentVehicle;
        } catch (NumberFormatException numberFormatException) {
            System.out.println("A numeric argument is out of bounds/of the wrong type");
        } catch (NoArgumentException noArgumentException) {
            System.out.println("An argument is missing");
        } catch (LessOrEqualToZeroException lessOrEqualToZeroException) {
            System.out.println("An argument does not match the given condition: greater than 0");
        } catch (IllegalArgumentException e) {
            System.out.println("Type/FuelType value is incorrect");
        }
        throw new ScriptBuildingException();
    }
}
