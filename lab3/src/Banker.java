public class Banker extends Unarmed {
    private final static Role role = Role.BANKER;

    private Banker(Human human) {
        super(human);
    }

    public static Banker initBanker(Human human) {
        if (human.getRole() == role) {
            return new Banker(human);
        } else {
            throw new NullPointerException("Incompatible roles: cannot turn " + human.getRole() + " into " + role + "!");
        }
    }
}
