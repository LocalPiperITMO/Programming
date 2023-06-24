package databasemanagement;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static utils.DatabaseQueries.VEHICLE_INIT_QUERY;

public class VehicleDatabaseInitializer extends AbstractDatabaseInitializer{

    public void createDatabase(Connection connection) {
        try (Statement statement = connection.createStatement()) {
            statement.execute(VEHICLE_INIT_QUERY);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}
