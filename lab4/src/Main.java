import BasicsForBuildings.Auction;
import BasicsForBuildings.Bank;
import BasicsForHumans.*;
import Exceptions.EmptyRobberyException;
import Exceptions.NamelessObjectException;
import Tools.Newspaper;

public class Main {
    public static void main(String[] args) throws EmptyRobberyException, NamelessObjectException {

        DateTracker day = new DateTracker();
        Auction auction = new Auction("Auction");
        Human neznayka = new Human("Neznayka", Role.AUCTIONEER);
        Human miga = new Human("Miga", Role.AUCTIONEER);
        Human kozlik = new Human("Kozlik", Role.AUCTIONEER);
        Julio julio = new Julio("Julio", Role.AUCTIONEER);
        Quarrel quarrel = new Quarrel();
        Newspaper news = new Newspaper();

        Quarrel.Side side1 = quarrel.new Side(julio, "Chairman");
        Quarrel.Side side2 = quarrel.new Side(miga, "Treasurer");
        quarrel.quarrelStart(side1, side2);
        side1.stateOpinion();
        side2.stateOpinion();
        quarrel.pacifyAttempt(neznayka);
        quarrel.pacifyAttempt(kozlik);

        day.startNewDay();
        OnDuty connector = new OnDuty();
        connector.setHuman(neznayka);
        connector.sellTo(null);
        connector.setHuman(julio);
        connector.goTo(auction);
        julio.explain(new Bank("Bank"));
        connector.setHuman(miga);
        connector.sellTo(null);

        day.startNewDay();
        news.explain(new Bank("Bank"));
    }
}