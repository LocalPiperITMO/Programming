public class Auctioneer extends Unarmed implements SellPapers {
    private final static Role role = Role.AUCTIONEER;

    private Auctioneer(Human human) {
        super(human);
    }

    public static Auctioneer initAuctioneer(Human human) {
        if (human.getRole() == role) {
            return new Auctioneer(human);
        } else {
            throw new NullPointerException("Incompatible roles: cannot turn " + human.getRole() + " into " + role + "!");
        }
    }

    public void transaction(Unarmed customer) {
        if (customer != null) {
            System.out.println("Transaction complete!");
        } else {
            System.out.println("There were no transactions: nobody came to buy papers.");
        }
    }
}
