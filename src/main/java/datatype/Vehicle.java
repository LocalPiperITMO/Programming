package datatype;

import java.time.LocalDate;
import java.util.Objects;

import static pattern.Receiver.sortingParameter;

public class Vehicle implements Comparable<Vehicle> {
    private final Integer id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private final java.time.LocalDate creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private long enginePower; //Значение поля должно быть больше 0
    private long fuelConsumption; //Значение поля должно быть больше 0
    private VehicleType type; //Поле может быть null
    private FuelType fuelType; //Поле может быть null

    public Vehicle(Integer id, String name, Coordinates coordinates, long enginePower, long fuelConsumption, VehicleType type, FuelType fuelType) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = LocalDate.now();
        this.enginePower = enginePower;
        this.fuelConsumption = fuelConsumption;
        this.type = type;
        this.fuelType = fuelType;
    }

    public Integer getId() {
        return id;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public void setEnginePower(long enginePower) {
        this.enginePower = enginePower;
    }

    public void setFuelConsumption(long fuelConsumption) {
        this.fuelConsumption = fuelConsumption;
    }

    public void setFuelType(FuelType fuelType) {
        this.fuelType = fuelType;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(VehicleType type) {
        this.type = type;
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
        if (Objects.equals(sortingParameter, "ID")) {
            return this.getId() - otherVehicle.getId();
        } else {
            int comparing1 = (this.getFuelType() == null) ? 0 : this.getFuelType().getPosition();
            int comparing2 = (otherVehicle.getFuelType() == null) ? 0 : otherVehicle.getFuelType().getPosition();
            return comparing1 - comparing2;
        }
    }
}
