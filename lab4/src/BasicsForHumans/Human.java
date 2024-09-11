package BasicsForHumans;


import Exceptions.NamelessObjectException;

public class Human {
    private final String name;
    private final int id;
    private State state;
    private final Role role;
    private final Coordinates location;
    private boolean is_hidden;


    public Human(String name, Role role) throws NamelessObjectException {
        String name1;
        try {
            if (name.length() != 0) {
                name1 = name;
            } else {
                throw new NamelessObjectException("Object does not have a name!");
            }
        } catch (NamelessObjectException n) {
            name1 = "Human";
        }
        this.name = name1;
        this.id = (int) (Math.random() * 1000);
            this.state = State.ALIVE;
            this.is_hidden = false;
            this.role = role;
            this.location = new Coordinates();
        }

        public String getName () {
            return this.name;
        }

        public int getId () {
            return this.id;
        }

        public State getState () {
            return this.state;
        }

        public Role getRole () {
            return this.role;
        }

        public void setState (State state){
            this.state = state;
        }

        public boolean getHidden () {
            return this.is_hidden;
        }

        public void setHidden ( boolean is_hidden){
            this.is_hidden = is_hidden;
        }

        public Coordinates getLocation () {
            return this.location;
        }


        @Override
        public boolean equals (Object obj){
            if (obj == this) {
                return true;
            } else if (obj == null) {
                return false;
            } else if (obj.getClass() == this.getClass()) {
                return true;
            } else {
                Human a = (Human) obj;
                return a.id == this.id;
            }
        }

        @Override
        public String toString () {
            return String.format("name: " + this.getName() + "\nid: " + this.getId() + "\nrole: " + this.getRole() + "\nx: " + this.getLocation().getX() + "\ny: " + this.getLocation().getY() + "\nz: " + this.getLocation().getZ());
        }

        @Override
        public int hashCode () {
            return this.id;
        }


    }
