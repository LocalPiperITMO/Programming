package commands;

import datatype.Vehicle;
import generators.IDGenerator;

import java.util.Vector;


public record Receiver(Vector<Vehicle> dataSet, java.nio.file.Path varAddress, IDGenerator idGenerator) {
}
