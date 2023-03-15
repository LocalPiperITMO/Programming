package commands;

import datatype.Vehicle;
import generators.IDGenerator;

import java.util.Vector;

/**
 * Receiver class
 * Stores the collection, file path and IDGenerator
 *
 * @param dataSet     is the collection
 * @param varAddress  path to file where collection was found (used for "save" command)
 * @param idGenerator generates unique ID for element (remembers every ID)
 */
public record Receiver(Vector<Vehicle> dataSet, java.nio.file.Path varAddress, IDGenerator idGenerator) {
}
