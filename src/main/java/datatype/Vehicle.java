package datatype;

import exceptions.LessOrEqualToZeroException;
import exceptions.NoArgumentException;

import java.time.LocalDate;

public class Vehicle implements Comparable<Vehicle> {
    private Integer id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates = new Coordinates(); //Поле не может быть null
    private final java.time.LocalDate creationDate = LocalDate.now(); //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private long enginePower; //Значение поля должно быть больше 0
    private long fuelConsumption; //Значение поля должно быть больше 0
    private VehicleType type; //Поле может быть null
    private FuelType fuelType; //Поле может быть null


    public Vehicle setId(int id) {
        this.id = id;
        return this;
    }

    public long getSum() {
        return (long) (getCoordinates().getX() + getCoordinates().getY() + getEnginePower() + getFuelConsumption());
    }

    public Integer getId() {
        return id;
    }

    public Vehicle setEnginePower(String arg) throws LessOrEqualToZeroException {
        long rawEnginePower = Long.parseLong(arg.trim());
        if (rawEnginePower <= 0) {
            throw new LessOrEqualToZeroException();
        } else {
            this.enginePower = rawEnginePower;
            return this;
        }
    }

    public Vehicle setFuelConsumption(String arg) throws LessOrEqualToZeroException {
        long rawFuelConsumption = Long.parseLong(arg.trim());
        if (rawFuelConsumption <= 0) {
            throw new LessOrEqualToZeroException();
        } else {
            this.fuelConsumption = rawFuelConsumption;
            return this;
        }
    }

    public Vehicle setFuelType(String arg) {
        if (arg.trim().length() == 0) {
            this.fuelType = null;
        } else {
            this.fuelType = FuelType.valueOf(arg.toUpperCase().trim());
        }
        return this;
    }

    public Vehicle setName(String arg) throws NoArgumentException {
        if (arg.trim().length() == 0) {
            throw new NoArgumentException();
        } else {
            this.name = arg;
            return this;
        }
    }

    public Vehicle setType(String arg) {
        if (arg.trim().length() == 0) {
            this.type = null;
        } else {
            this.type = VehicleType.valueOf(arg.toUpperCase().trim());
        }
        return this;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public long getEnginePower() {
        return enginePower;
    }

    public long getFuelConsumption() {
        return fuelConsumption;
    }

    public VehicleType getType() {
        return type;
    }

    public FuelType getFuelType() {
        return fuelType;
    }

    public String toString() {
        return getId() + " " + getName() + " " + getCreationDate() + " "
                + getCoordinates().getX() + " " + getCoordinates().getY() + " " + getEnginePower() + " " + getFuelConsumption() + " "
                + getType() + " " + getFuelType();
    }

    public int compareTo(Vehicle otherVehicle) {
        return this.getId() - otherVehicle.getId();
    }
}
