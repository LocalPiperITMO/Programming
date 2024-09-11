package clienthandling;

import clienthandlingfacade.ClientOperationSelector;
import databasemanagement.observertools.DatabaseObserver;
import databasemanagement.observertools.VehicleDatabase;
import datatypes.Vehicle;
import exceptions.DeclineDatabaseConnectionException;
import wrapping.CommandInstructionsPacket;

import java.io.IOException;

public class ClientHandler {
    private final int port;

    public static VehicleDatabase vehicleDatabase;
    public static DatabaseObserver<Vehicle> concurrentHashMapObserver;
    public static CommandInstructionsPacket CIP;

    public ClientHandler(int port, String configPath, String commandListPath) throws DeclineDatabaseConnectionException {
        this.port = port;
        DatabaseInstaller databaseProcessor = new DatabaseInstaller(configPath);
        vehicleDatabase = databaseProcessor.getDatabase();
        CommandListInstaller commandListProcessor = new CommandListInstaller();
        commandListProcessor.prepareCommands(commandListPath);
        CIP = commandListProcessor.getClientInstructionsPacket();

    }

    public void start() {
        try {
            ClientOperationSelector operationSelector = new ClientOperationSelector(port);
            operationSelector.beginSelectionLoop();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

