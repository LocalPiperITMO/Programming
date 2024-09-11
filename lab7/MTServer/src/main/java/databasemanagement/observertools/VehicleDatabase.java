package databasemanagement.observertools;

import datatypes.Vehicle;
import exceptions.DMLBuildException;
import receivers.TextReceiver;
import utils.DMLQueryBuilder;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static utils.DatabaseQueries.*;


public class VehicleDatabase extends AbstractDatabase<Vehicle> {
    private final DatabaseObservable<Vehicle> databaseObservable;
    private final Connection connection;
    private final Map.Entry<String, List<String>> headers;
    private String username;
    private final TextReceiver receiver = new TextReceiver();

    public VehicleDatabase(Connection connection, DatabaseObservable<Vehicle> databaseObservable, Map.Entry<String, List<String>> headers) {
        this.connection = connection;
        this.databaseObservable = databaseObservable;
        this.headers = headers;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    private boolean checkAccess(String storedUsername) {
        return !Objects.equals(username, storedUsername);
    }

    private void resetSequence() throws SQLException {
        receiver.printToLog(this.getClass().getSimpleName(), "Resetting id sequence...");
        Statement statement = connection.createStatement();
        statement.executeQuery("SELECT setval('vehicles_id_seq',(SELECT MAX(id) FROM vehicles));");
    }

    @Override
    public boolean insertElement(Vehicle element) {
        try {
            DMLQueryBuilder queryBuilder = new DMLQueryBuilder();
            List<String> headersForInsertion = new ArrayList<>(headers.getValue());
            headersForInsertion.remove("id");
            headersForInsertion.remove("creation_date");
            String query = queryBuilder
                    .query(ABSTRACT_INSERT_QUERY_WORDS)
                    .table(headers.getKey())
                    .columns(headersForInsertion)
                    .row(
                            "'" + element.getName() + "'",
                            element.getCoordinates().getX(),
                            element.getCoordinates().getY(),
                            element.getEnginePower(),
                            element.getFuelConsumption(),
                            element.getType() == null ? "null" : "'" + element.getType() + "'",
                            element.getFuelType() == null ? "null" : "'" + element.getFuelType() + "'",
                            "'" + username + "'")
                    .build();
            receiver.printToLog(this.getClass().getSimpleName(), "Query: " + query);
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.executeUpdate();

            ResultSet resultSet = statement.getGeneratedKeys();
            int generatedKey = 0;
            if (resultSet.next()) {
                generatedKey = resultSet.getInt(1);
            }
            element.setCreationDate(LocalDate.parse(resultSet.getString(5)));
            databaseObservable.notifyObservers(new DatabaseEvent<>(DatabaseEventType.ADD, element, generatedKey));
            resetSequence();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            new TextReceiver().printToLog("ERROR", "Something went wrong trying to execute command");
        } catch (DMLBuildException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public boolean updateElement(Vehicle element, int index) {
        try {
            PreparedStatement statement;
            int rowsAffected = 0;

            String getQuery = "SELECT * FROM " + headers.getKey() + " WHERE " + headers.getValue().get(0) + "=?";
            receiver.printToLog(this.getClass().getSimpleName(), "Query: " + getQuery);
            PreparedStatement getStatement = connection.prepareStatement(getQuery);
            getStatement.setInt(1, index);
            ResultSet resultSet = getStatement.executeQuery();
            if (resultSet.next() && resultSet.getInt(1) > 0) {
                element.setCreationDate(LocalDate.parse(resultSet.getString(5)));
                if (checkAccess(resultSet.getString("owner"))) {
                    return false;
                }
                DMLQueryBuilder queryBuilder = new DMLQueryBuilder();
                List<String> headersForInsertion = new ArrayList<>(headers.getValue());
                headersForInsertion.remove("id");
                headersForInsertion.remove("creation_date");
                String updateQuery = queryBuilder
                        .query(ABSTRACT_UPDATE_QUERY_WORDS)
                        .table(headers.getKey())
                        .columns(headersForInsertion)
                        .row(
                                "'" + element.getName() + "'",
                                element.getCoordinates().getX(),
                                element.getCoordinates().getY(),
                                element.getEnginePower(),
                                element.getFuelConsumption(),
                                element.getType() == null ? "null" : "'" + element.getType() + "'",
                                element.getFuelType() == null ? "null" : "'" + element.getFuelType() + "'",
                                "'" + username + "'")
                        .predicate("id", "=", index)
                        .build();
                receiver.printToLog(this.getClass().getSimpleName(), "Query: " + updateQuery);
                statement = connection.prepareStatement(updateQuery);
                rowsAffected = statement.executeUpdate();
            }
            if (rowsAffected > 0) {
                databaseObservable.notifyObservers(new DatabaseEvent<>(DatabaseEventType.UPDATE, element, index));
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            new TextReceiver().printToLog("ERROR", "Something went wrong trying to execute command");
        } catch (DMLBuildException e) {
            throw new RuntimeException(e);
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteElement(Vehicle element, int index) {
        try {
            String checkQuery = "SELECT * FROM " + headers.getKey() + " WHERE " + headers.getValue().get(0) + "=?";
            receiver.printToLog(this.getClass().getSimpleName(), "Query: " + checkQuery);
            PreparedStatement statement = connection.prepareStatement(checkQuery);
            statement.setInt(1, index);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String ownerName = resultSet.getString("owner");
                if (checkAccess(ownerName)) {
                    return false;
                }
            }
            DMLQueryBuilder queryBuilder = new DMLQueryBuilder();
            String query = queryBuilder
                    .query(ABSTRACT_DELETE_QUERY_WORDS)
                    .table(headers.getKey())
                    .predicate(headers.getValue().get(0), "=", index)
                    .build();
            receiver.printToLog(this.getClass().getSimpleName(), "Query: " + query);
            statement = connection.prepareStatement(query);
            int numberOfDeleted = statement.executeUpdate();
            if (numberOfDeleted > 0) {
                databaseObservable.notifyObservers(new DatabaseEvent<>(DatabaseEventType.DELETE, element, index));
            }
            resetSequence();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            new TextReceiver().printToLog("ERROR", "Something went wrong trying to execute command");
        } catch (DMLBuildException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public Connection getConnection() {
        return connection;
    }
}
