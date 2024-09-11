public class Human implements Coordinates {
    private String name;
    private Role role;
    private int coord_x;
    private int coord_y;
    private int coord_z;

    public Human(String name, Role role) {
        this.name = name;
        this.role = role;
        this.coord_x = (int) (Math.random() * 150);
        this.coord_y = (int) (Math.random() * 150);
        this.coord_z = (int) (Math.random() * 150);
    }

    public String getName() {
        return this.name;
    }

    public Role getRole() {
        return this.role;
    }

    public int[] getLocation() {
        return new int[]{this.coord_x, this.coord_y, this.coord_z};
    }

    public void goTo(Building building) {
        this.coord_x = building.getLocation()[0];
        this.coord_y = building.getLocation()[1];
        this.coord_z = building.getLocation()[2];
        System.out.println(this.getName() + " went to " + building.getName());
    }
}
