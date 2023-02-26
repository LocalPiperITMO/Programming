package converters;

import datatype.Coordinates;
import datatype.FuelType;
import datatype.Vehicle;
import datatype.VehicleType;
import exceptions.LessOrEqualToZeroException;
import generators.IDGenerator;
import validators.ConversionValidator;

import java.util.List;
import java.util.Objects;
import java.util.Vector;

public class StringListToObjectVectorConverter {
    List<String[]> text;
    Vector<Vehicle> dataSet;
    ConversionValidator validator;
    IDGenerator idGenerator;

    public StringListToObjectVectorConverter(List<String[]> text) {
        this.dataSet = new Vector<>();
        this.text = text;
        this.validator = new ConversionValidator();
        this.idGenerator = new IDGenerator();

    }

    public Vector<Vehicle> convertStringListToObjectVector() {
        int corruptedLines = 0;


        int id;
        String name;
        Coordinates coordinates;
        long enginePower;
        long fuelConsumption;
        VehicleType type;
        FuelType fuelType;
        int lineCounter = 0;
        for (String[] line : this.text) {
            ++lineCounter;
            try {
                if (line.length == 7) {
                    name = line[0].strip();
                    coordinates = new Coordinates(Float.parseFloat(line[1]), Integer.parseInt(line[2]));
                    enginePower = Long.parseLong(line[3].strip());
                    fuelConsumption = Long.parseLong(line[4].strip());
                    if (enginePower <= 0 || fuelConsumption <= 0) {
                        throw new LessOrEqualToZeroException();
                    }
                    line[5] = (Objects.equals(line[5].strip(), "")) ? null : line[5].strip();
                    line[6] = (Objects.equals(line[6].strip(), "")) ? null : line[6].strip();
                    type = (line[5] == null) ? null : VehicleType.valueOf(line[5].toUpperCase());
                    fuelType = (line[6] == null) ? null : FuelType.valueOf(line[6].toUpperCase());
                    id = idGenerator.generateRandomID();
                    dataSet.add(new Vehicle(id, name, coordinates, enginePower, fuelConsumption, type, fuelType));
                } else {
                    validator.invalidNumberOfArguments(lineCounter);
                    ++corruptedLines;
                }
            } catch (NumberFormatException ex) {
                validator.nonNumericArgument(lineCounter);
                ++corruptedLines;
            } catch (LessOrEqualToZeroException zero) {
                validator.lessOrEqualToZeroArgument(lineCounter);
                ++corruptedLines;
            } catch (NullPointerException npe) {
                validator.forbiddenNullArgument(lineCounter);
                ++corruptedLines;
            } catch (IllegalArgumentException iae) {
                validator.invalidTypeArgument(lineCounter);
                ++corruptedLines;
            }
        }
        System.out.println("Dataset is ready for use. Number of corrupted lines: " + corruptedLines);
        return dataSet;
    }
}
