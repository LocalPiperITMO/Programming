package datatype;

/**
 * FuelType<br>
 * Argument type, used for storing name of fuel type used by element
 */
public enum FuelType {
    KEROSENE(1),
    MANPOWER(2),
    NUCLEAR(3),
    PLASMA(4),
    ANTIMATTER(5);
    private final int position;

    /**
     * @param position used for comparing fuel types (used by "print_field_ascending_fuel_type" command)
     */
    FuelType(int position) {
        this.position = position;
    }

    /**
     * @return position
     */
    public int getPosition() {
        return this.position;
    }
}
