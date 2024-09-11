public class Main {
    public static void main(String[] args) {
        Auctioneer neznayka = Auctioneer.initAuctioneer(new Human("Neznayka", Role.AUCTIONEER));
        Auctioneer miga = Auctioneer.initAuctioneer(new Human("Miga", Role.AUCTIONEER));
        Auctioneer kozlik = Auctioneer.initAuctioneer(new Human("Kozlik", Role.AUCTIONEER));
        neznayka.transaction(null);
        Julio julio = new Julio("Julio");
        julio.goTo(new Auction("Auction"));
        julio.explain(new Bank("Bank"));
    }
}
