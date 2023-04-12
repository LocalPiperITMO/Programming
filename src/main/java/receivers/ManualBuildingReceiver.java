package receivers;

import datatype.Vehicle;
import exceptions.LessOrEqualToZeroException;
import exceptions.NoArgumentException;
import generators.IDGenerator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ManualBuildingReceiver {
    private final IDGenerator idGenerator;
    private final TextReceiver textReceiver;
    private final BufferedReader reader;

    public ManualBuildingReceiver(IDGenerator idGenerator, TextReceiver textReceiver) {
        this.idGenerator = idGenerator;
        this.textReceiver = textReceiver;
        this.reader = new BufferedReader(new InputStreamReader(System.in));
    }

    private void showMessageToUser(String message) {
        textReceiver.print(message);
    }

    class VehicleBuilder {
        private final Vehicle vehicle;

        public VehicleBuilder() {
            this.vehicle = new Vehicle();
        }

        private void requestNameUntilFits() {
            while (true) {
                try {
                    showMessageToUser("Input name (must not be empty):");
                    vehicle.setName(reader.readLine());
                    return;
                } catch (NoArgumentException noArgumentException) {
                    showMessageToUser("Wrong input! Please, try again");
                } catch (IOException ioException) {
                    showMessageToUser("Something went wrong, try again");
                }
            }
        }

        private void requestCoordinateXUntilFits() {
            while (true) {
                try {
                    showMessageToUser("Input x (Float value, -2 000 000 <=x<=2 000 000, must not be empty):");
                    vehicle.getCoordinates().setX(reader.readLine());
                    return;
                } catch (NumberFormatException numberFormatException) {
                    showMessageToUser("Wrong input! Please, try again");
                } catch (IOException ioException) {
                    showMessageToUser("Something went wrong, try again");
                }
            }
        }

        private void requestCoordinateYUntilFits() {
            while (true) {
                try {
                    showMessageToUser("Input y (Integer value, -2 000 000 000<=x<=2 000 000 000, must not be empty):");
                    vehicle.getCoordinates().setY(reader.readLine());
                    return;
                } catch (NumberFormatException numberFormatException) {
                    showMessageToUser("Wrong input! Please, try again");
                } catch (IOException ioException) {
                    showMessageToUser("Something went wrong, try again");
                }
            }
        }

        private void requestEnginePowerUntilFits() {
            while (true) {
                try {
                    showMessageToUser("Input enginePower (Long value, 0<y<=4 000 000 000, must not be null):");
                    vehicle.setEnginePower(reader.readLine());
                    return;
                } catch (LessOrEqualToZeroException | NumberFormatException illegalArgumentException) {
                    showMessageToUser("Wrong input! Please, try again");
                } catch (IOException ioException) {
                    showMessageToUser("Something went wrong, try again");
                }
            }
        }

        private void requestFuelConsumptionUntilFits() {
            while (true) {
                try {
                    showMessageToUser("Input fuelConsumption (Long value, 0<y<=4 000 000 000, must not be null):");
                    vehicle.setFuelConsumption(reader.readLine());
                    return;
                } catch (LessOrEqualToZeroException | NumberFormatException illegalArgumentException) {
                    showMessageToUser("Wrong input! Please, try again");
                } catch (IOException ioException) {
                    showMessageToUser("Something went wrong, try again");
                }
            }
        }

        private void requestVehicleTypeUntilFits() {
            while (true) {
                try {
                    showMessageToUser("Input vehicleType (leave blank if null, one of those: PLANE, HELICOPTER, BOAT, BICYCLE, CHOPPER):");
                    vehicle.setType(reader.readLine());
                    return;
                } catch (IllegalArgumentException illegalArgumentException) {
                    showMessageToUser("Wrong input! Please, try again");
                } catch (IOException ioException) {
                    showMessageToUser("Something went wrong, try again");
                }
            }
        }

        private void requestFuelTypeUntilFits() {
            while (true) {
                try {
                    showMessageToUser("Input fuelType (leave blank if null, one of those: KEROSENE, MANPOWER, NUCLEAR, PLASMA, ANTIMATTER):");
                    vehicle.setFuelType(reader.readLine());
                    return;
                } catch (IllegalArgumentException illegalArgumentException) {
                    showMessageToUser("Wrong input! Please, try again");
                } catch (IOException ioException) {
                    showMessageToUser("Something went wrong, try again");
                }
            }
        }

        public Vehicle buildStepByStep() {
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

    public Vehicle build() {
        VehicleBuilder vehicleBuilder = new VehicleBuilder();
        return vehicleBuilder.buildStepByStep();
    }
}