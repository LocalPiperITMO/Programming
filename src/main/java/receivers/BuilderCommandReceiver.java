package receivers;

import collection.CollectionStorage;
import datatype.Vehicle;
import exceptions.InvalidArgumentsWhileVehicleBuildingViaScriptException;
import exceptions.LessOrEqualToZeroException;
import exceptions.NoArgumentException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Receiver class
 * Stores realization for building commands (such as 'add','add_if_max','update id' and 'remove_if_greater')
 */
public class BuilderCommandReceiver {
    /**
     * Stores Vehicle vector
     */
    private final CollectionStorage storage;
    /**
     * Stores mode
     * If true, building Vehicles is done using script arguments, otherwise user input
     */
    private boolean scriptMode;
    /**
     * Arguments for building via script
     */
    private List<String> arguments;

    public BuilderCommandReceiver(CollectionStorage storage) {
        this.storage = storage;
        this.scriptMode = false;
        this.arguments = new ArrayList<>();
    }

    /**
     * Switches modes
     *
     * @param scriptMode new mode
     */
    public void setScriptMode(boolean scriptMode) {
        this.scriptMode = scriptMode;
    }

    /**
     * Sets new list of arguments
     *
     * @param arguments new list of arguments
     */
    public void setArguments(List<String> arguments) {
        this.arguments = arguments;
    }

    /**
     * Builds instances of Vehicle using user input (if scriptMode=false)
     * Private method
     *
     * @param vehicleBase instance of Vehicle
     * @return the same instance but with tweaked arguments
     */
    private Vehicle buildVehicleViaUserInput(Vehicle vehicleBase) throws IOException {
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
            System.out.println("Input x (Float value, -2 000 000 000<=x<=2 000 000 000, must not be empty):");
            try {
                vehicleBase.getCoordinates().setX(reader.readLine());
            } catch (NumberFormatException e) {
                System.out.println("Wrong input! Try again");
                isAllowedToProceed = false;
            }
        } while (!isAllowedToProceed);
        do {
            isAllowedToProceed = true;
            System.out.println("Input y (Integer value, -2 000 000 000<=y<=2 000 000 000, must not be empty):");
            try {
                vehicleBase.getCoordinates().setY(reader.readLine());
            } catch (NumberFormatException e) {
                System.out.println("Wrong input! Try again");
                isAllowedToProceed = false;
            }
        } while (!isAllowedToProceed);
        do {
            isAllowedToProceed = true;
            System.out.println("Input enginePower (Long value, 0<y<=4 000 000 000, must not be null):");
            try {
                vehicleBase.setEnginePower(reader.readLine());
            } catch (LessOrEqualToZeroException | NumberFormatException e) {
                System.out.println("Wrong input! Try again");
                isAllowedToProceed = false;
            }
        } while (!isAllowedToProceed);
        do {
            isAllowedToProceed = true;
            System.out.println("Input fuelConsumption (Long value, 0<y<=4 000 000 000, must not be null):");
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
     * Builds instances of Vehicle using script arguments (if scriptMode=true)
     * Private method
     *
     * @param arguments list of arguments
     * @return instance of Vehicle
     * @throws InvalidArgumentsWhileVehicleBuildingViaScriptException if script building went wrong
     */

    private Vehicle buildVehicleViaScript(List<String> arguments) throws InvalidArgumentsWhileVehicleBuildingViaScriptException {
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
     * 'add' command realization
     *
     * @return command execution report (sent to TextReceiver)
     * @throws InvalidArgumentsWhileVehicleBuildingViaScriptException if script building went wrong
     */
    public String addVehicle() throws IOException, InvalidArgumentsWhileVehicleBuildingViaScriptException {
        Vehicle vehicle;
        if (scriptMode) {
            vehicle = buildVehicleViaScript(arguments);
        } else {
            vehicle = buildVehicleViaUserInput(new Vehicle());
        }
        vehicle.setId(storage.getIdGenerator().generateRandomID());
        storage.getDataSet().add(vehicle);
        return "Vehicle added successfully!";
    }

    /**
     * 'add_if_max' command realization
     *
     * @return command execution report (sent to TextReceiver)
     * @throws InvalidArgumentsWhileVehicleBuildingViaScriptException if script building went wrong
     */
    public String addIfMax() throws InvalidArgumentsWhileVehicleBuildingViaScriptException, IOException {
        Vehicle vehicle;
        if (scriptMode) {
            vehicle = buildVehicleViaScript(arguments);
        } else {
            vehicle = buildVehicleViaUserInput(new Vehicle());
        }
        vehicle.setId(storage.getIdGenerator().generateRandomID());
        Collections.sort(storage.getDataSet());
        if (vehicle.compareTo(storage.getDataSet().lastElement()) > 0) {
            storage.getDataSet().add(vehicle);
            return "New element added successfully";
        }
        return "New element has not been added: element with ID " + storage.getDataSet().lastElement().getId() + " is greater";
    }

    /**
     * 'remove_greater' command realization
     *
     * @return command execution report (sent to TextReceiver)
     * @throws InvalidArgumentsWhileVehicleBuildingViaScriptException if script building went wrong
     */
    public String removeGreater() throws InvalidArgumentsWhileVehicleBuildingViaScriptException, IOException {
        Vehicle vehicle;
        if (scriptMode) {
            vehicle = buildVehicleViaScript(arguments);
        } else {
            vehicle = buildVehicleViaUserInput(new Vehicle());
        }
        vehicle.setId(storage.getIdGenerator().generateRandomID());
        storage.getDataSet().removeIf(vehicleToCompare -> vehicleToCompare.compareTo(vehicle) > 0);
        return "Greater elements removed successfully";
    }

    /**
     * 'update id' command realization
     *
     * @return command execution report (sent to TextReceiver)
     * @throws InvalidArgumentsWhileVehicleBuildingViaScriptException if script building went wrong
     */
    public String update(int id) throws InvalidArgumentsWhileVehicleBuildingViaScriptException, IOException {
        boolean isFound = false;
        Vehicle vehicle = new Vehicle();
        for (Vehicle vehicleToFind : storage.getDataSet()) {
            if (vehicleToFind.getId() == id) {
                vehicle = vehicleToFind;
                isFound = true;
                break;
            }
        }
        if (!isFound) {
            return "Element with given ID does not exist";
        }
        if (scriptMode) {
            vehicle = buildVehicleViaScript(arguments);
        } else {
            buildVehicleViaUserInput(vehicle);
        }
        return "Vehicle by ID " + vehicle.getId() + " updated successfully";
    }
}
