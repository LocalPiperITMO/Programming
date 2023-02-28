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
            String name = nameCheck(this.data[0].trim());
            Coordinates coordinates = new Coordinates(xCheck(this.data[1].trim()), yCheck(this.data[2].trim()));
            long enginePower = powerOrConsumptionCheck(this.data[3].trim());
            long fuelConsumption = powerOrConsumptionCheck(this.data[4].trim());
            VehicleType type = typeCheck(this.data[5].trim());
            FuelType fuelType = fuelTypeCheck(this.data[6].trim());
            if (!creationForbidden) {
                return new Vehicle(idGenerator.generateRandomID(), name, coordinates, enginePower, fuelConsumption, type, fuelType);
            }
        } else {
            validator.invalidNumberOfArguments(this.lineIndex);
        }
        creationForbidden = false;
        return null;
    }

    public Vehicle createObjectByUserInput() throws IOException {
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
            name = nameCheck(reader.readLine().trim());
        } while (!proceedToNextStep);
        do {
            proceedToNextStep = true;
            System.out.println("Input coordinates (input x and y, must be numbers, separated by whitespace): ");
            String[] rawInput = reader.readLine().split(" ", 2);
            if (rawInput.length < 2) {
                System.out.println("Wrong input!");
                proceedToNextStep = false;
            } else {
                coordinates = new Coordinates(xCheck(rawInput[0]), yCheck(rawInput[1]));
            }
        } while (!proceedToNextStep);
        do {
            proceedToNextStep = true;
            System.out.println("Input enginePower (must be a number, greater than 0): ");
            enginePower = powerOrConsumptionCheck(reader.readLine());
        } while (!proceedToNextStep);
        do {
            proceedToNextStep = true;
            System.out.println("Input fuelCapacity (must be a number, greater than 0): ");
            fuelConsumption = powerOrConsumptionCheck(reader.readLine());
        } while (!proceedToNextStep);
        do {
            proceedToNextStep = true;
            System.out.println("Input vehicleType (choose one of the following: PLANE,HELICOPTER,BOAT,BICYCLE,CHOPPER):" +
                    "\nNOTE: Field can be blank");
            type = typeCheck(reader.readLine());
        } while (!proceedToNextStep);
        do {
            proceedToNextStep = true;
            System.out.println("Input fuelType (choose one of the following: KEROSENE,MANPOWER,NUCLEAR,PLASMA,ANTIMATTER):" +
                    "\nNOTE: Field can be blank");
            fuelType = fuelTypeCheck(reader.readLine());
        } while (!proceedToNextStep);
        return new Vehicle(idGenerator.generateRandomID(), name, coordinates, enginePower, fuelConsumption, type, fuelType);
    }

    public void consecutiveInputMode(boolean allow) {
        this.allowConsecutiveInput = allow;
    }

    public void stateSwitchesForObjectCreation() {
        if (!allowConsecutiveInput) {
            creationForbidden = true;
        } else {
            proceedToNextStep = false;
        }
    }


    public String nameCheck(String arg) {
        if (arg != null && !arg.equals("")) {
            return arg;
        }
        stateSwitchesForObjectCreation();
        validator.forbiddenNullArgument(this.lineIndex);
        return "";
    }

    public float xCheck(String arg) {
        try {
            return Float.parseFloat(arg);
        } catch (NumberFormatException e) {
            stateSwitchesForObjectCreation();
            validator.nonNumericArgument(this.lineIndex);
        }
        return 0;
    }

    public int yCheck(String arg) {
        try {
            return Integer.parseInt(arg);
        } catch (NumberFormatException e) {
            stateSwitchesForObjectCreation();
            validator.nonNumericArgument(this.lineIndex);
        }
        return 0;
    }

    public long powerOrConsumptionCheck(String arg) {
        try {
            long enginePower = Long.parseLong(arg);
            if (enginePower <= 0) {
                throw new LessOrEqualToZeroException();
            }
            return enginePower;
        } catch (NumberFormatException e) {
            stateSwitchesForObjectCreation();
            validator.nonNumericArgument(this.lineIndex);
        } catch (LessOrEqualToZeroException e) {
            stateSwitchesForObjectCreation();
            validator.lessOrEqualToZeroArgument(this.lineIndex);
        }
        return 0;
    }

    public VehicleType typeCheck(String arg) {
        if (!Objects.equals(arg, "")) {
            try {
                return VehicleType.valueOf(arg);
            } catch (IllegalArgumentException e) {
                stateSwitchesForObjectCreation();
                validator.invalidTypeArgument(this.lineIndex);
            }
        }
        return null;
    }

    public FuelType fuelTypeCheck(String arg) {
        if (!Objects.equals(arg, "")) {
            try {
                return FuelType.valueOf(arg);
            } catch (IllegalArgumentException e) {
                stateSwitchesForObjectCreation();
                validator.invalidTypeArgument(this.lineIndex);
            }
        }
        return null;
    }
}
