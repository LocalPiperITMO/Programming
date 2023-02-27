package datatype;

public enum FuelType {
    KEROSENE(1),
    MANPOWER(2),
    NUCLEAR(3),
    PLASMA(4),
    ANTIMATTER(5);
    private final int position;

    FuelType(int position) {
        this.position = position;
    }

    public int getPosition() {
        return this.position;
    }
}
