package utils;

import java.sql.*;
import java.util.*;

import static utils.DatabaseQueries.ABSTRACT_GRABBER_QUERY;

public class DatabaseDataGrabber {
    public Optional<Map.Entry<String, List<String>>> grabColumns(Connection connection, String tableName) {
        String query = ABSTRACT_GRABBER_QUERY;
        query = query.replace("<table_name>", tableName);
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();

            ResultSetMetaData metaData = resultSet.getMetaData();
            int column_count = metaData.getColumnCount();
            List<String> columnList = new ArrayList<>();
            for (int i = 1; i <= column_count; ++i) {
                columnList.add(metaData.getColumnName(i));
            }
            return Optional.of(new AbstractMap.SimpleEntry<>(tableName, columnList));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
