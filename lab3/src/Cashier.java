public class Cashier extends Unarmed implements HideAndSeek {

    private boolean is_hidden;
    private final static Role role = Role.CASHIER;

    private Cashier(Human human) {
        super(human);
        this.is_hidden = false;
    }

    public static Cashier initCashier(Human human) {
        if (human.getRole() == role) {
            return new Cashier(human);
        } else {
            throw new NullPointerException("Incompatible roles: cannot turn " + human.getRole() + " into " + role + "!");
        }
    }

    public void hide(String place_to_hide) {
        System.out.println(this.getName() + " chose a place to hide: " + place_to_hide);
        this.is_hidden = true;
    }

    public boolean isHidden() {
        return this.is_hidden;
    }
}
