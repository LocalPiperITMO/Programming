package databasemanagement;

import exceptions.DeclineDatabaseConnectionException;
import utils.ConfigDataGrabber;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {

    public Connection connectToDataBase(String configPath) throws DeclineDatabaseConnectionException {
        try {
            Class.forName("org.postgresql.Driver");
            ConfigDataGrabber grabber = new ConfigDataGrabber();
            grabber.grabData(configPath);
            return DriverManager.getConnection(grabber.getDbURL(), grabber.getUsername(), grabber.getPassword());
        } catch (ClassNotFoundException | SQLException e) {
            throw new DeclineDatabaseConnectionException();
        }

    }
}
