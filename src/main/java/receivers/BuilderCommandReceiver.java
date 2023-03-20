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

public class BuilderCommandReceiver {
    private final CollectionStorage storage;
    private boolean scriptMode;
    private List<String> arguments;

    public BuilderCommandReceiver(CollectionStorage storage) {
        this.storage = storage;
        this.scriptMode = false;
        this.arguments = new ArrayList<>();
    }

    public void setScriptMode(boolean scriptMode) {
        this.scriptMode = scriptMode;
    }

    public void setArguments(List<String> arguments) {
        this.arguments = arguments;
    }

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

    public void addVehicle() throws IOException, InvalidArgumentsWhileVehicleBuildingViaScriptException {
        Vehicle vehicle;
        if (scriptMode) {
            vehicle = buildVehicleViaScript(arguments);
        } else {
            vehicle = buildVehicleViaUserInput(new Vehicle());
        }
        vehicle.setId(storage.getIdGenerator().generateRandomID());
        storage.getDataSet().add(vehicle);
        System.out.println("Vehicle added successfully!");
    }

    public void addIfMax() throws InvalidArgumentsWhileVehicleBuildingViaScriptException, IOException {
        Vehicle vehicle;
        if (scriptMode) {
            vehicle = buildVehicleViaScript(arguments);
        } else {
            vehicle = buildVehicleViaUserInput(new Vehicle());
        }
        vehicle.setId(storage.getIdGenerator().generateRandomID());
        int index = 0;
        do {
            if (storage.getDataSet().get(index).getSum() > storage.getDataSet().get(index + 1).getSum()) {
                Collections.swap(storage.getDataSet(), index, index + 1);
                index = 0;
            } else {
                ++index;
            }
        } while (index != storage.getDataSet().size() - 1);
        if (vehicle.compareTo(storage.getDataSet().lastElement()) > 0) {
            storage.getDataSet().add(vehicle);
            System.out.println("New element added successfully");
        } else {
            System.out.println("New element has not been added: element with ID " + storage.getDataSet().lastElement().getId() + " is greater");
        }
    }

    public void removeGreater() throws InvalidArgumentsWhileVehicleBuildingViaScriptException, IOException {
        Vehicle vehicle;
        if (scriptMode) {
            vehicle = buildVehicleViaScript(arguments);
        } else {
            vehicle = buildVehicleViaUserInput(new Vehicle());
        }
        vehicle.setId(storage.getIdGenerator().generateRandomID());
        storage.getDataSet().removeIf(vehicleToCompare -> vehicleToCompare.getSum() > vehicle.getSum());
        System.out.println("Done");
    }

    public void update(int id) throws InvalidArgumentsWhileVehicleBuildingViaScriptException, IOException {
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
            System.out.println("Element with given ID does not exist");
        } else {
            if (scriptMode) {
                vehicle = buildVehicleViaScript(arguments);
            } else {
                buildVehicleViaUserInput(vehicle);
            }
            System.out.println("Vehicle by ID " + vehicle.getId() + " updated successfully");

        }
    }

}
