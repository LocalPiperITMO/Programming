public class Policeman extends AbstractRole implements ArmedAndDangerous {
    private final String weapon;
    private final static Role role = Role.POLICEMAN;

    private Policeman(Human human) {
        super(human);
        this.weapon = weapons[(int) (Math.random() * weapons.length)];
    }

    public static Policeman initPoliceman(Human human) {
        if (human.getRole() == role) {
            return new Policeman(human);
        } else {
            throw new NullPointerException("Incompatible roles: cannot turn " + human.getRole() + " into " + role + "!");
        }


    }

    public String getWeapon() {
        return this.weapon;
    }

    public void shoot(AbstractRole bandit) {
        if (this.getState() != State.DEAD && bandit.getState() != State.DEAD && bandit.getRole() == Role.BANDIT) {
            if (Math.random() * 100 >= 40) {
                System.out.println(bandit.getName() + " was killed by " + this.getName() + " with " + this.getWeapon() + "!");
                bandit.setState(State.DEAD);
            } else if (Math.random() * 100 >= 10) {
                System.out.println(bandit.getName() + " was injured by " + this.getName() + " with " + this.getWeapon() + "!");
                bandit.setState(State.INJURED);
            } else {
                System.out.println("Somehow, " + bandit.getName() + " survived the attack of " + this.getName() + "!");
            }
        }
    }
}
