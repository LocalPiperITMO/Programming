package datatype;

import java.time.LocalDate;

public class Vehicle {
    private Integer id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.time.LocalDate creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
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
}
