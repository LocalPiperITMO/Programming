public abstract class Building implements Coordinates {
    private final String name;
    private final Role role_of_building;
    private int coord_x;
    private int coord_y;
    private int coord_z;

    public Building(String name,Role role) {
        this.name = name;
        this.role_of_building = role;
        this.coord_x = (int) (Math.random() * 150);
        this.coord_y = (int) (Math.random() * 150);
        this.coord_z = (int) (Math.random() * 150);
    }

    public Role getRole() {
        return this.role_of_building;
    }


    public int[] getLocation() {
        return new int[]{this.coord_x, this.coord_y, this.coord_z};
    }

    public String getName() {
        return this.name;
    }
}
