public class Bandit extends AbstractRole implements ArmedAndDangerous {
    private final String weapon;
    private final static Role role = Role.BANDIT;

    private Bandit(Human human) {
        super(human);
        this.weapon = weapons[(int) (Math.random() * weapons.length)];
    }

    public static Bandit initBandit(Human human) {
        if (human.getRole() == role) {
            return new Bandit(human);
        } else {
            throw new NullPointerException("Incompatible roles: cannot turn " + human.getRole() + " into " + role + "!");
        }


    }

    public String getWeapon() {
        return this.weapon;
    }

    public void shoot(AbstractRole victim) {
        if (this.getState() != State.DEAD && victim.getState() != State.DEAD) {
            if (!victim.isHidden()) {
                if (victim.equals(this)) {
                    System.out.println(this.getName() + " killed himself with " + this.getWeapon() + "!");
                    this.setState(State.DEAD);
                } else if (!(victim instanceof Unarmed)) {
                    if (Math.random() * 100 >= 20) {
                        System.out.println(victim.getName() + " was killed by " + this.getName() + " with " + this.getWeapon() + "!");
                        victim.setState(State.DEAD);
                    } else if (Math.random() * 100 >= 1) {
                        System.out.println(victim.getName() + " was injured by " + this.getName() + " with " + this.getWeapon() + "!");
                        victim.setState(State.INJURED);
                    } else {
                        System.out.println("Somehow, " + victim.getName() + " survived the attack of " + this.getName() + "!");
                    }
                } else {
                    if (Math.random() * 100 >= 50) {
                        System.out.println(victim.getName() + " was killed by " + this.getName() + " with " + this.getWeapon() + "!");
                        victim.setState(State.DEAD);
                    } else if (Math.random() * 100 >= 10) {
                        System.out.println(victim.getName() + " was injured by " + this.getName() + " with " + this.getWeapon() + "!");
                        victim.setState(State.INJURED);
                    } else {
                        System.out.println("Somehow, " + victim.getName() + " survived the attack of " + this.getName() + "!");
                    }
                }
            } else {
                System.out.println(victim.getName() + " survived the attack of " + this.getName() + " because " + victim.getName() + " is hiding!");
            }
        }
    }
}
