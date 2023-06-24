package databasemanagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static utils.DatabaseQueries.ABSTRACT_GRABBER_QUERY;

public class DatabaseLoader {

    public ResultSet loadDataFromDatabase(Connection connection, String tableName) {
        String query = ABSTRACT_GRABBER_QUERY;
        query = query.replace("<table_name>", tableName);
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            return statement.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }
}
