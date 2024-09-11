public abstract class AbstractRole {
    private final String name;
    private State state;
    private boolean is_hidden;
    private final Role role;
    private int coord_x;
    private int coord_y;
    private int coord_z;

    public AbstractRole(Human human) {
        this.name = human.getName();
        this.state = State.ALIVE;
        this.role = human.getRole();
        this.is_hidden = false;
        this.coord_x = human.getLocation()[0];
        this.coord_y = human.getLocation()[1];
        this.coord_z = human.getLocation()[2];
    }

    public String getName() {
        return this.name;
    }


    public Role getRole() {
        return this.role;
    }

    public void setState(State state) {
        this.state = state;
    }

    public State getState() {
        return this.state;
    }

    public boolean isHidden() {
        return this.is_hidden;
    }
}
