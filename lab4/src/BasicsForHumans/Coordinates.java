package BasicsForHumans;

public class Coordinates {
    private int x;
    private int y;
    private int z;

    public Coordinates() {
        this.x = (int) (Math.random() * 150);
        this.y = (int) (Math.random() * 150);
        this.z = (int) (Math.random() * 150);
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getZ() {
        return this.z;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setZ(int z) {
        this.z = z;
    }
}
