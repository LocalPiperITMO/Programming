package pattern;

import datatype.Vehicle;
import generators.ObjectGenerator;

import java.util.Vector;


public record Receiver(Vector<Vehicle> dataSet, java.nio.file.Path varAddress, ObjectGenerator vehicleFactory) {
}
