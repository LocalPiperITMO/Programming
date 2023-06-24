package utils;

public class DatabaseQueries {
    public static String VEHICLE_INIT_QUERY = """
                    CREATE TABLE IF NOT EXISTS users (
                                    uid SERIAL PRIMARY KEY,
                                    login VARCHAR(50) NOT NULL UNIQUE,
                                    password_hash BYTEA NOT NULL,
                                    salt BYTEA NOT NULL
                                );
                    CREATE TABLE IF NOT EXISTS vehicles (
                                    id SERIAL PRIMARY KEY,
                                    vehicle_name TEXT NOT NULL CHECK (vehicle_name <> '') CHECK (LENGTH(vehicle_name) <= 20),
                                    coordinates_x FLOAT NOT NULL CHECK (ABS(coordinates_x) <= 2000000),
                                    coordinates_y INT NOT NULL CHECK (ABS(coordinates_y) <= 2000000000),
                                    creation_date DATE NOT NULL DEFAULT CURRENT_DATE,
                                    engine_power BIGINT NOT NULL CHECK (engine_power > 0 AND engine_power <= 4000000000),
                                    fuel_consumption BIGINT NOT NULL CHECK (fuel_consumption > 0 AND fuel_consumption <= 4000000000),
                                    vehicle_type VARCHAR(20),
                                    fuel_type VARCHAR(20),
                                    owner VARCHAR(50) REFERENCES users(login) NOT NULL
                                );
                    SELECT setval('vehicles_id_seq',(SELECT MAX(id) FROM vehicles));
                    """;
    public static String ABSTRACT_GRABBER_QUERY = "SELECT * FROM <table_name>;";

    public static String ABSTRACT_INSERT_QUERY_WORDS = "INSERT INTO VALUES";
    public static String ABSTRACT_UPDATE_QUERY_WORDS = "UPDATE SET WHERE AND";
    public static String ABSTRACT_DELETE_QUERY_WORDS = "DELETE FROM WHERE AND";

}
