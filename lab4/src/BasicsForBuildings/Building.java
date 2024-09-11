package BasicsForBuildings;

import BasicsForHumans.Coordinates;
import Exceptions.NamelessObjectException;

public abstract class Building {
    private final String name;
    private final int id;
    private final Coordinates location;

    public Building(String name) throws NamelessObjectException {
        String name1;
        try {
            if (name.length() != 0) {
                name1 = name;
            } else {
                throw new NamelessObjectException("Object does not have a name!");
            }
        } catch (NamelessObjectException n) {
            name1 = "Building";
        }
        this.name = name1;
        this.id = (int) (Math.random() * 1000 + 1000);
        this.location = new Coordinates();
    }

    public String getName() {
        return this.name;
    }

    public int getId() {
        return this.id;
    }

    public Coordinates getLocation() {
        return this.location;
    }

    public int getCash() {
        class CashRegister {
            private int money;

            public CashRegister() {
                this.money = (int) (Math.random() * 10000000);
            }

            public int getMoney() {
                int stolen = this.money;
                this.money = 0;
                return stolen;
            }

        }
        CashRegister cash_register = new CashRegister();
        return cash_register.getMoney();
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
            Building a = (Building) obj;
            return a.id == this.id;
        }
    }


    @Override
    public String toString() {
        return String.format("name: " + this.getName() + "\nid: " + this.getId() + "\nx: " + this.getLocation().getX() + "\ny: " + this.getLocation().getY() + "\nz: " + this.getLocation().getZ());
    }

    @Override
    public int hashCode() {
        return this.id;
    }
}
