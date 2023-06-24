package clienthandlingfacade;


import exceptions.UnauthorizedAccessException;
import receivers.TextReceiver;
import wrapping.AuthorizationPacket;
import wrapping.CommandDescriptionPacket;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Optional;

import static clienthandling.ClientHandler.vehicleDatabase;

public class ClientAuthorizer {
    private final TextReceiver receiver = new TextReceiver();

    public Optional<AuthorizationPacket> checkPacket(byte[] clientData) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bis = new ByteArrayInputStream(clientData);
        ObjectInputStream ois = new ObjectInputStream(bis);
        Object object = ois.readObject();
        if (object instanceof AuthorizationPacket authPacket) {
            receiver.printToLog(this.getClass().getSimpleName(), "Received auth packet");
            return Optional.of(authPacket);
        }
        return Optional.empty();
    }

    public boolean authorizeClient(Serializable packet) throws SQLException, UnauthorizedAccessException, NoSuchAlgorithmException {
        if (packet instanceof AuthorizationPacket authPacket) {
            String name = authPacket.getUsername();
            String password = authPacket.getHashedPassword();
            if (authPacket.getRegFlag()) {
                return register(name, password);
            }
            return authorize(name, password);
        } else if (packet instanceof CommandDescriptionPacket cdp) {
            receiver.printToLog(this.getClass().getSimpleName(), "Confirming authorization...");
            String name = cdp.getUsername();
            String password = cdp.getPassword();
            if (authorize(name, password)) {
                return true;
            }
            receiver.printToLog(this.getClass().getSimpleName(), "UNKNOWN USER, TERMINATE NOW");
            throw new UnauthorizedAccessException();
        }
        return false;
    }


    private boolean register(String name, String password) throws SQLException, NoSuchAlgorithmException {
        Connection connection = vehicleDatabase.getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE login=?");
        statement.setString(1, name);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            receiver.printToLog(this.getClass().getSimpleName(), "Failed to register new user: username already in use");
            return false;
        }
        SecureRandom secureRandom = new SecureRandom();
        byte[] salt = new byte[16];
        secureRandom.nextBytes(salt);
        byte[] hashedPassword = hashPassword(password, salt);
        statement = connection.prepareStatement("INSERT INTO users(login, password_hash, salt) VALUES (?,?,?)");
        statement.setString(1, name);
        statement.setBytes(2, hashedPassword);
        statement.setBytes(3, salt);
        statement.execute();
        receiver.printToLog(this.getClass().getSimpleName(), "Registration successful");
        return true;
    }

    private boolean authorize(String name, String password) throws SQLException, NoSuchAlgorithmException {
        Connection connection = vehicleDatabase.getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE login=?");
        statement.setString(1, name);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            byte[] storedPasswordBytes = resultSet.getBytes("password_hash");
            byte[] storedSalt = resultSet.getBytes("salt");
            byte[] hashedPassword = hashPassword(password, storedSalt);
            if (Arrays.equals(hashedPassword, storedPasswordBytes)) {
                receiver.printToLog(this.getClass().getSimpleName(), "Authorization successful");
                return true;
            }
        }
        receiver.printToLog(this.getClass().getSimpleName(), "Username/password is invalid");
        return false;
    }

    private byte[] hashPassword(String password, byte[] salt) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        md.update(salt);
        return md.digest(password.getBytes());
    }

}
