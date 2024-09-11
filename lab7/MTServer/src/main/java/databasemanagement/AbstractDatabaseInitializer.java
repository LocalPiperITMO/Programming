package databasemanagement;

import java.sql.Connection;

public abstract class AbstractDatabaseInitializer {
    public abstract void createDatabase(Connection connection);
}
