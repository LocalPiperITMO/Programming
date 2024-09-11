package clienthandling;

import collectionmanagement.AbstractCollectionParser;
import collectionmanagement.VehicleCollectionParser;
import databasemanagement.AbstractDatabaseInitializer;
import databasemanagement.DatabaseConnector;
import databasemanagement.DatabaseLoader;
import databasemanagement.VehicleDatabaseInitializer;
import databasemanagement.observertools.DatabaseObservable;
import databasemanagement.observertools.DatabaseObserver;
import databasemanagement.observertools.VehicleDatabase;
import datatypes.Vehicle;
import exceptions.DeclineDatabaseConnectionException;
import receivers.TextReceiver;
import utils.DatabaseDataGrabber;

import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class DatabaseInstaller {
    private VehicleDatabase database;

    public DatabaseInstaller(String configPath) throws DeclineDatabaseConnectionException {
        connectToDataBase(configPath);
    }

    private void connectToDataBase(String configPath) throws DeclineDatabaseConnectionException {
        TextReceiver receiver = new TextReceiver();
        receiver.printToLog(this.getClass().getSimpleName(), "Connecting to database...");

        DatabaseConnector connector = new DatabaseConnector();
        AbstractDatabaseInitializer initializer = new VehicleDatabaseInitializer();
        AbstractCollectionParser<Vehicle> parser = new VehicleCollectionParser();
        DatabaseDataGrabber grabber = new DatabaseDataGrabber();
        DatabaseLoader loader = new DatabaseLoader();
        Connection connection = connector.connectToDataBase(configPath);
        initializer.createDatabase(connection);
        Optional<Map.Entry<String, List<String>>> setOfHeaders = grabber.grabColumns(connection, "vehicles");

        if (setOfHeaders.isPresent()) {
            DatabaseObservable<Vehicle> databaseObservable = new DatabaseObservable<>();
            ClientHandler.concurrentHashMapObserver = new DatabaseObserver<>(
                    parser.parseToMap(
                            loader.loadDataFromDatabase(
                                    connection, "vehicles"),
                            setOfHeaders.get().getValue())
            );
            databaseObservable.attach(ClientHandler.concurrentHashMapObserver);

            database = new VehicleDatabase(connection, databaseObservable, setOfHeaders.get());
            receiver.printToLog(this.getClass().getSimpleName(), "Connection attempt successful");
        } else {
            database = null;
            receiver.printToLog(this.getClass().getSimpleName(), "There is no database to work with");
        }
    }

    public VehicleDatabase getDatabase() {
        return database;
    }

}
