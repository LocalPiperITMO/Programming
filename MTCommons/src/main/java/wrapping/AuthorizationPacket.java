package wrapping;

import java.io.Serializable;

public class AuthorizationPacket implements Serializable {
    private final String username;
    private final String password;
    private final boolean isReg;

    public AuthorizationPacket(String username, String password, boolean isReg) {
        this.username = username;
        this.password = password;
        this.isReg = isReg;
    }

    public String getUsername() {
        return username;
    }

    public String getHashedPassword() {
        return password;
    }

    public boolean getRegFlag() {
        return isReg;
    }
}
