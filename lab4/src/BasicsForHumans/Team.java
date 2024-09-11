package BasicsForHumans;

import BasicsForBuildings.Building;
import Interfaces.MoveSomewhere;

public class Team implements MoveSomewhere {
    private final String name;
    private final Coordinates location;
    private Human[] members = new Human[0];
    private final int id;
    private final Role condition;

    public Team(String name, Role condition) {
        this.name = name;
        this.condition = condition;
        this.id = (int) (Math.random() * 1000 + 2000);
        this.location = new Coordinates();
    }

    public String getName() {
        return this.name;
    }

    public int getId() {
        return this.id;
    }

    public Role getCondition() {
        return this.condition;
    }

    public Coordinates getLocation() {
        return this.location;
    }

    public Human[] getMembers() {
        return this.members;
    }

    public void join(Human member) {
        if (member.getRole() == this.getCondition() || ((member.getRole() == Role.CASHIER || member.getRole() == Role.BANKER) && this.getCondition() == Role.AUCTIONEER) || ((member.getRole() == Role.CASHIER || member.getRole() == Role.AUCTIONEER) && this.getCondition() == Role.BANKER)) {
            Human[] new_list = new Human[this.getMembers().length + 1];
            for (int i = 0; i < this.getMembers().length; ++i) {
                new_list[i] = this.getMembers()[i];
            }
            new_list[this.getMembers().length] = member;
            this.members = new_list;
            member.getLocation().setX(getLocation().getX());
            member.getLocation().setY(getLocation().getY());
            member.getLocation().setZ(getLocation().getZ());
        }
    }

    public void goTo(Building building) {
        try {
            for (Human member : members) {
                member.getLocation().setX(building.getLocation().getX());
                member.getLocation().setY(building.getLocation().getY());
                member.getLocation().setZ(building.getLocation().getZ());
            }
            getLocation().setX(building.getLocation().getX());
            getLocation().setY(building.getLocation().getY());
            getLocation().setZ(building.getLocation().getZ());
            if (members.length > 0) {
                System.out.println("Team " + getName() + " arrives to " + building.getName());
            }
        } catch (NullPointerException n) {
            for (Human member : members) {
                member.getLocation().setX((int) (Math.random() * 100));
                member.getLocation().setY((int) (Math.random() * 100));
                member.getLocation().setZ((int) (Math.random() * 100));
            }
            getLocation().setX((int) (Math.random() * 100));
            getLocation().setY((int) (Math.random() * 100));
            getLocation().setZ((int) (Math.random() * 100));
            System.out.println("Team " + getName() + " left");
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (obj == null) {
            return false;
        } else if (obj.getClass() == this.getClass()) {
            return true;
        } else {
            Team a = (Team) obj;
            return a.id == this.id;
        }
    }

    @Override
    public String toString() {
        return String.format("team_name: " + this.getName() + "\nid: " + this.getId() + "\nrole: " + this.getCondition() + "\namount_of_members: " + getMembers().length + "\nx: " + this.getLocation().getX() + "\ny: " + this.getLocation().getY() + "\nz: " + this.getLocation().getZ());
    }

    @Override
    public int hashCode() {
        return this.getMembers().length;
    }
}
