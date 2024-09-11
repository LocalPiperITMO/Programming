package collection;

import datatype.Vehicle;
import generators.IDGenerator;

import java.util.Vector;

/**
 * Class for storing collection and IDGenerator
 */
public class CollectionStorage {
    private final Vector<Vehicle> dataSet;
    private final IDGenerator idGenerator;

    /**
     * Receives collection and IDGenerator
     * @param vector vector of Vehicles
     * @param idGenerator already existing IDGenerator
     */
    public CollectionStorage(Vector<Vehicle> vector, IDGenerator idGenerator) {
        this.dataSet = vector;
        this.idGenerator = idGenerator;
    }

    /**
     * @return vector of Vehicles
     */
    public Vector<Vehicle> getDataSet() {
        return this.dataSet;
    }

    /**
     * @return IDGenerator
     */
    public IDGenerator getIdGenerator() {
        return idGenerator;
    }
}
