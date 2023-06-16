package data;

import exceptions.LessOrEqualToZeroException;
import exceptions.NoArgumentException;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Element of collection, has arguments that are set separately
 */
public class Vehicle implements Serializable,Comparable<Vehicle> {

    private Integer id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически

    private String name; //Поле не может быть null, Строка не может быть пустой

    private final Coordinates coordinates = new Coordinates(); //Поле не может быть null

    private final java.time.LocalDate creationDate = LocalDate.now(); //Поле не может быть null, Значение этого поля должно генерироваться автоматически

    private long enginePower; //Значение поля должно быть больше 0

    private long fuelConsumption; //Значение поля должно быть больше 0

    private VehicleType type; //Поле может быть null

    private FuelType fuelType; //Поле может быть null

    /**
     * ID is set by IDGenerator<br>
     * Used for chaining
     *
     * @param id new ID
     * @return this instance of Vehicle
     */
    public Vehicle setId(int id) {
        this.id = id;
        return this;
    }

    /**
     * @return ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets new enginePower. Returns Vehicle if successful, otherwise throws corresponding exception<br>
     * Used for chaining
     *
     * @param arg new enginePower
     * @return this instance of Vehicle
     * @throws LessOrEqualToZeroException if argument is less or equal to zero
     */
    public Vehicle setEnginePower(String arg) throws LessOrEqualToZeroException {
        long rawEnginePower = Long.parseLong(arg.trim());
        if (rawEnginePower <= 0) {
            throw new LessOrEqualToZeroException();
        } else if (rawEnginePower > Long.parseLong("4000000000")) {
            throw new NumberFormatException();
        }
        this.enginePower = rawEnginePower;
        return this;
    }


    /**
     * Sets new fuelConsumption. Returns Vehicle if successful, otherwise throws corresponding exception<br>
     * Used for chaining
     *
     * @param arg new fuelConsumption
     * @return this instance of Vehicle
     * @throws LessOrEqualToZeroException if argument is less or equal to zero
     */
    public Vehicle setFuelConsumption(String arg) throws LessOrEqualToZeroException {
        long rawFuelConsumption = Long.parseLong(arg.trim());
        if (rawFuelConsumption <= 0) {
            throw new LessOrEqualToZeroException();
        } else if (rawFuelConsumption > Long.parseLong("4000000000")) {
            throw new NumberFormatException();
        } else {
            this.fuelConsumption = rawFuelConsumption;
            return this;
        }
    }

    /**
     * Sets new fuelType. Returns Vehicle if successful, otherwise throws corresponding exception<br>
     * Used for chaining
     *
     * @param arg new fuelType
     * @return this instance of Vehicle
     */
    public Vehicle setFuelType(String arg) {
        if (arg.trim().length() == 0) {
            this.fuelType = null;
        } else {
            this.fuelType = FuelType.valueOf(arg.toUpperCase().trim());
        }
        return this;
    }

    /**
     * Sets new name. Returns Vehicle if successful, otherwise throws corresponding exception<br>
     * Used for chaining
     *
     * @param arg new name
     * @return this instance of Vehicle
     */
    public Vehicle setName(String arg) throws NoArgumentException {
        if (arg.trim().length() == 0 || arg.length() > 20) {
            throw new NoArgumentException();
        } else {
            this.name = arg;
            return this;
        }
    }

    /**
     * Sets new vehicleType. Returns Vehicle if successful, otherwise throws corresponding exception<br>
     * Used for chaining
     *
     * @param arg new vehicleType
     * @return this instance of Vehicle
     */
    public Vehicle setType(String arg) {
        if (arg.trim().length() == 0) {
            this.type = null;
        } else {
            this.type = VehicleType.valueOf(arg.toUpperCase().trim());
        }
        return this;
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @return coordinates
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     * @return date of creation
     */
    public LocalDate getCreationDate() {
        return creationDate;
    }

    /**
     * @return enginePower
     */
    public long getEnginePower() {
        return enginePower;
    }

    /**
     * @return fuelConsumption
     */
    public long getFuelConsumption() {
        return fuelConsumption;
    }

    /**
     * @return vehicleType
     */
    public VehicleType getType() {
        return type;
    }

    /**
     * @return fuelType
     */
    public FuelType getFuelType() {
        return fuelType;
    }

    /**
     * Used for presenting element of collection (used by "show","print_ascending","print_field_ascending_fuel_type","reorder" commands)
     *
     * @return string representation of element
     */
    public String toString() {
        return String.format("%7d " + "%-20s " + "%12s " + "%15.6f " + "%11d " + "%15d " + "%15d " + "%-10s " + "%-10s",
                getId(),
                getName(),
                getCreationDate(),
                getCoordinates().getX(),
                getCoordinates().getY(),
                getEnginePower(),
                getFuelConsumption(),
                getType(),
                getFuelType());
    }

    /**
     * Compares elements by enginePower. If they are equal, compares by fuelConsumption
     *
     * @param otherVehicle the object to be compared.
     * @return result of comparison
     */
    public int compareTo(Vehicle otherVehicle) {
        int result = Long.compare(this.getEnginePower(), otherVehicle.getEnginePower());
        if (result == 0) {
            result = Long.compare(this.getFuelConsumption(), otherVehicle.getFuelConsumption());
            if (result == 0) {
                result = Integer.compare(this.getId(), otherVehicle.getId());
            }
        }
        return result;
    }
}
