package generators;

import datatype.Coordinates;
import datatype.FuelType;
import datatype.Vehicle;
import datatype.VehicleType;
import exceptions.LessOrEqualToZeroException;
import validators.ConversionValidator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

public class ObjectGenerator {
    ConversionValidator validator = new ConversionValidator();
    boolean allowConsecutiveInput = false;
    boolean creationForbidden = false;
    boolean proceedToNextStep = true;
    String[] data;
    int lineIndex;
    final IDGenerator idGenerator = new IDGenerator();

    public Vehicle createObjectByData(String[] data, int lineIndex) {
        this.lineIndex = lineIndex;
        if (data.length == 7) {
            this.data = data;
            String name = getName(this.data[0].trim());
            Coordinates coordinates = new Coordinates(getX(this.data[1].trim()), getY(this.data[2].trim()));
            long enginePower = getPowerOrConsumption(this.data[3].trim());
            long fuelConsumption = getPowerOrConsumption(this.data[4].trim());
            VehicleType type = getVehicleType(this.data[5].trim());
            FuelType fuelType = getFuelType(this.data[6].trim());
            if (!creationForbidden) {
                return new Vehicle(idGenerator.generateRandomID(), name, coordinates, enginePower, fuelConsumption, type, fuelType);
            }
        } else {
            validator.invalidNumberOfArguments(this.lineIndex);
        }
        creationForbidden = false;
        return null;
    }

    public Vehicle createOrUpdateObjectByUserInput(Vehicle vehicle) throws IOException {
        String name;
        Coordinates coordinates = null;
        long enginePower;
        long fuelConsumption;
        VehicleType type;
        FuelType fuelType;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        do {
            proceedToNextStep = true;
            System.out.println("Input name: ");
            name = getName(reader.readLine().trim());
        } while (!proceedToNextStep);
        do {
            proceedToNextStep = true;
            System.out.println("Input coordinates (input x and y, must be numbers, separated by whitespace): ");
            String[] rawInput = reader.readLine().split(" ", 2);
            if (rawInput.length < 2) {
                System.out.println("Wrong input!");
                proceedToNextStep = false;
            } else {
                coordinates = new Coordinates(getX(rawInput[0].trim()), getY(rawInput[1].trim()));
            }
        } while (!proceedToNextStep);
        do {
            proceedToNextStep = true;
            System.out.println("Input enginePower (must be a number, greater than 0): ");
            enginePower = getPowerOrConsumption(reader.readLine().trim());
        } while (!proceedToNextStep);
        do {
            proceedToNextStep = true;
            System.out.println("Input fuelCapacity (must be a number, greater than 0): ");
            fuelConsumption = getPowerOrConsumption(reader.readLine().trim());
        } while (!proceedToNextStep);
        do {
            proceedToNextStep = true;
            System.out.println("Input vehicleType (choose one of the following: PLANE,HELICOPTER,BOAT,BICYCLE,CHOPPER):" +
                    "\nNOTE: Field can be blank");
            type = getVehicleType(reader.readLine().trim());
        } while (!proceedToNextStep);
        do {
            proceedToNextStep = true;
            System.out.println("Input fuelType (choose one of the following: KEROSENE,MANPOWER,NUCLEAR,PLASMA,ANTIMATTER):" +
                    "\nNOTE: Field can be blank");
            fuelType = getFuelType(reader.readLine().trim());
        } while (!proceedToNextStep);
        if (vehicle == null) {
            return new Vehicle(idGenerator.generateRandomID(), name, coordinates, enginePower, fuelConsumption, type, fuelType);
        } else {
            vehicle.setName(name);
            vehicle.setCoordinates(coordinates);
            vehicle.setEnginePower(enginePower);
            vehicle.setFuelConsumption(fuelConsumption);
            vehicle.setType(type);
            vehicle.setFuelType(fuelType);
        }
        return null;
    }

    public void switchToConsecutiveInputMode(boolean allow) {
        this.allowConsecutiveInput = allow;
    }

    public void switchStateForVehicleCreationProcess() {
        if (!allowConsecutiveInput) {
            creationForbidden = true;
        } else {
            proceedToNextStep = false;
        }
    }


    public String getName(String arg) {
        if (arg != null && !arg.equals("")) {
            return arg;
        }
        switchStateForVehicleCreationProcess();
        validator.forbiddenNullArgument(this.lineIndex);
        return "";
    }

    public float getX(String arg) {
        try {
            return Float.parseFloat(arg);
        } catch (NumberFormatException e) {
            switchStateForVehicleCreationProcess();
            validator.nonNumericArgument(this.lineIndex);
        }
        return 0;
    }

    public int getY(String arg) {
        try {
            return Integer.parseInt(arg);
        } catch (NumberFormatException e) {
            switchStateForVehicleCreationProcess();
            validator.nonNumericArgument(this.lineIndex);
        }
        return 0;
    }

    public long getPowerOrConsumption(String arg) {
        try {
            long enginePower = Long.parseLong(arg);
            if (enginePower <= 0) {
                throw new LessOrEqualToZeroException();
            }
            return enginePower;
        } catch (NumberFormatException e) {
            switchStateForVehicleCreationProcess();
            validator.nonNumericArgument(this.lineIndex);
        } catch (LessOrEqualToZeroException e) {
            switchStateForVehicleCreationProcess();
            validator.lessOrEqualToZeroArgument(this.lineIndex);
        }
        return 0;
    }

    public VehicleType getVehicleType(String arg) {
        if (!Objects.equals(arg, "")) {
            try {
                return VehicleType.valueOf(arg);
            } catch (IllegalArgumentException e) {
                switchStateForVehicleCreationProcess();
                validator.invalidTypeArgument(this.lineIndex);
            }
        }
        return null;
    }

    public FuelType getFuelType(String arg) {
        if (!Objects.equals(arg, "")) {
            try {
                return FuelType.valueOf(arg);
            } catch (IllegalArgumentException e) {
                switchStateForVehicleCreationProcess();
                validator.invalidTypeArgument(this.lineIndex);
            }
        }
        return null;
    }
}
