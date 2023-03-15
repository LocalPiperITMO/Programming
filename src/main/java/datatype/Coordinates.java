package datatype;

/**
 * Coordinates
 * Argument type, stores X and Y coordinates
 */
public class Coordinates {
    private Float x;
    private int y;

    public float getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Coordinates setX(String arg) {
        this.x = Float.parseFloat(arg.trim());
        return this;
    }

    public Coordinates setY(String arg) {
        this.y = Integer.parseInt(arg.trim());
        return this;
    }
}
