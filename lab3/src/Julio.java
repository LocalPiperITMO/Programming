public class Julio {
    private final String name;
    private int coord_x;
    private int coord_y;
    private int coord_z;

    public Julio(String name) {
        this.name = name;
        this.coord_x = (int) (Math.random() * 150);
        this.coord_y = (int) (Math.random() * 150);
        this.coord_z = (int) (Math.random() * 150);
    }

    public String getName() {
        return this.name;
    }

    public void goTo(Building building) {
        this.coord_x = building.getLocation()[0];
        this.coord_y = building.getLocation()[1];
        this.coord_z = building.getLocation()[2];
        System.out.println(this.getName() + " went to " + building.getName());
    }

    public void explain(Building building) {
        System.out.println(this.getName() + " begins his story.\nThe story goes as follows");
        RobberyManager robbery = new RobberyManager(building);
        robbery.preparation();
        robbery.robberyStart();
        robbery.robberyEnd();
        System.out.println("That's what happened in " + building.getName() + " that day!");
    }

}
