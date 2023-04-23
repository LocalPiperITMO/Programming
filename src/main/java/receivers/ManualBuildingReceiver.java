package receivers;

import datatype.Vehicle;
import exceptions.BuildingInterruptionException;
import exceptions.LessOrEqualToZeroException;
import exceptions.NoArgumentException;
import generators.IDGenerator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

/**
 * Building receiver<br>
 * Builds objects using manual input<br>
 * User can stop the process by entering "/stop"
 */
public class ManualBuildingReceiver {
    private final IDGenerator idGenerator;
    private final TextReceiver textReceiver;
    private final BufferedReader reader;

    /**
     * Gets IDGenerator for building purposes and text receiver for printing output
     *
     * @param idGenerator  generates random ID for objects
     * @param textReceiver prints output
     */
    public ManualBuildingReceiver(IDGenerator idGenerator, TextReceiver textReceiver) {
        this.idGenerator = idGenerator;
        this.textReceiver = textReceiver;
        this.reader = new BufferedReader(new InputStreamReader(System.in));
    }

    private void showMessageToUser(String message) {
        textReceiver.print(message);
    }

    private class VehicleBuilder {
        private final Vehicle vehicle;

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

        private Vehicle buildStepByStep() throws BuildingInterruptionException {
            requestNameUntilFits();
            requestCoordinateXUntilFits();
            requestCoordinateYUntilFits();
            requestEnginePowerUntilFits();
            requestFuelConsumptionUntilFits();
            requestVehicleTypeUntilFits();
            requestFuelTypeUntilFits();
            vehicle.setId(idGenerator.generateRandomID());
            return vehicle;
        }
    }

    /**
     * Creates VehicleBuilder objects and calls its build method<br>
     * Inside vehicle is created, then modified by user, then returned
     *
     * @return built vehicle
     * @throws BuildingInterruptionException if user types "/stop" during building process
     */
    public Vehicle build() throws BuildingInterruptionException {
        VehicleBuilder vehicleBuilder = new VehicleBuilder();
        return vehicleBuilder.buildStepByStep();
    }
}