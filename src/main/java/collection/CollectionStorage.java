package collection;

import datatype.Vehicle;
import generators.IDGenerator;

import java.util.Vector;

public class CollectionStorage {
    private final Vector<Vehicle> dataSet;
    private final IDGenerator idGenerator;

    public CollectionStorage(Vector<Vehicle> vector, IDGenerator idGenerator) {
        this.dataSet = vector;
        this.idGenerator = idGenerator;
    }

    public Vector<Vehicle> getDataSet() {
        return this.dataSet;
    }

    public IDGenerator getIdGenerator() {
        return idGenerator;
    }
}
