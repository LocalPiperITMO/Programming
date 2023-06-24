package uih_utils;

import java.io.Console;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class PasswordGetter {
    private String password;
    private final String username;

    public PasswordGetter(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void requestPassword() throws NoSuchAlgorithmException {
        Console console = System.console();
        if (console == null) {
            System.err.println("No console found");
            throw new IllegalStateException("Cannot read password");
        }
        char[] passwordChars = console.readPassword("Enter your password, " + username + ":");
        if (passwordChars == null) {
            System.out.println("No password entered");
            throw new IllegalArgumentException("Password not provided");
        }
        password = new String(passwordChars);
        java.util.Arrays.fill(passwordChars, ' ');
    }

    private String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        byte[] bytes = md.digest(password.getBytes());
        return new String(Base64.getEncoder().encode(bytes));
    }
}
