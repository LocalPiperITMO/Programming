package commands;

import datatype.Vehicle;

import java.util.Vector;


public record Receiver(Vector<Vehicle> dataSet, java.nio.file.Path varAddress, generators.VehicleBuilder vehicleFactory) {
}
