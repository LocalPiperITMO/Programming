package data;

import java.io.Serializable;

/**
 * Coordinates<br>
 * Argument type, stores X and Y coordinates
 */
public class Coordinates implements Serializable {

    private Float x;

    private int y;

    /**
     * @return x
     */
    public float getX() {
        return x;
    }

    /**
     * @return y
     */
    public int getY() {
        return y;
    }

    /**
     * Sets new X, returning Coordinates if successful, otherwise throws corresponding exception<br>
     * Used for chaining.
     *
     * @param arg new argument
     * @return this instance of Coordinates
     */
    public Coordinates setX(String arg) {
        x = Float.parseFloat(arg.trim());
        if (Float.compare(x, -2000000) < 0 || Float.compare(x, 2000000) > 0) {
            throw new NumberFormatException();
        }
        return this;
    }

    /**
     * Sets new Y, returning Coordinates if successful, otherwise throws corresponding exception<br>
     * Used for chaining.
     *
     * @param arg new argument
     * @return this instance of Coordinates
     */
    public Coordinates setY(String arg) {
        this.y = Integer.parseInt(arg.trim());
        if (y < -2000000000 || y > 2000000000) {
            throw new NumberFormatException();
        }
        return this;
    }
}
