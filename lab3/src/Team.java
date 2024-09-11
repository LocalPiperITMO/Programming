public class Team implements Coordinates {
    private final String name;
    private final Role role;

    private AbstractRole[] members;
    private int coord_x;
    private int coord_y;
    private int coord_z;

    public Team(String name, Role role) {
        this.name = name;
        this.role = role;
        this.members = new AbstractRole[0];
        this.coord_x = (int) (Math.random() * 150);
        this.coord_y = (int) (Math.random() * 150);
        this.coord_z = (int) (Math.random() * 150);
    }

    public int[] getLocation() {
        return new int[]{this.coord_x, this.coord_y, this.coord_z};
    }

    public String getName() {
        return this.name;
    }

    public void addToTeam(AbstractRole member) {
        if (member.getRole() == this.role || ((this.role == Role.BANKER || this.role == Role.CASHIER || this.role == Role.AUCTIONEER) && member instanceof Unarmed)) {
            AbstractRole[] new_members = new AbstractRole[this.members.length + 1];
            for (int index = 0; index < this.members.length; ++index) {
                new_members[index] = this.members[index];
            }
            new_members[this.members.length] = member;
            this.members = new_members;
        } else {
            throw new NullPointerException("Incompatible roles: cannot add " + member.getRole() + " to team of " + this.role + "!");
        }
    }

    public AbstractRole[] getMembers() {
        return this.members;
    }

    public void goTo(Building building) {
        this.coord_x = building.getLocation()[0];
        this.coord_y = building.getLocation()[1];
        this.coord_z = building.getLocation()[2];
        System.out.println(this.getName() + " went to " + building.getName() + "!");
    }


}
