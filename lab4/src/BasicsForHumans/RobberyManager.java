package BasicsForHumans;

import BasicsForBuildings.Auction;
import BasicsForBuildings.Bank;
import BasicsForBuildings.Building;
import Exceptions.EmptyRobberyException;
import Exceptions.NamelessObjectException;
import Tools.MoneyBag;

public class RobberyManager {
    private final OnDuty connector = new OnDuty();
    private final Team victims = new Team("Workers", Role.BANKER);
    private final Team bandits = new Team("Bandits", Role.BANDIT);
    private final Team police = new Team("Police", Role.POLICEMAN);
    private int dead_counter;
    private int injured_counter;

    public RobberyManager() throws NamelessObjectException {
    }

    public void preparation(Building building, int bandit_numbers) throws NamelessObjectException {
        victims.goTo(building);
        if (building.getClass() == Auction.class) {
            for (int i = 1; i < 50; ++i) {
                victims.join(new Human(String.format("Worker %d", i), Role.AUCTIONEER));
            }
        } else if (building.getClass() == Bank.class) {
            for (int i = 1; i < 50; ++i) {
                victims.join(new Human(String.format("Worker %d", i), Role.BANKER));
            }
        }
        victims.join(new Human("Cashier", Role.CASHIER));
        for (int i = 1; i <= bandit_numbers; ++i) {
            bandits.join(new Human(String.format("Bandit %d", i), Role.BANDIT));
            police.join(new Human(String.format("Policeman %d", i), Role.POLICEMAN));

        }
        System.out.println(victims.getName() + " were working at " + building.getName() + " when " + bandits.getName() + " in the amount of " + bandit_numbers + " arrived!");
        bandits.goTo(building);
        connector.setHuman(victims.getMembers()[victims.getMembers().length - 1]);
        connector.hide("Closet");
    }

    public void threatening(Team attackers, Team defenders) {
        for (Human bandit : attackers.getMembers()) {
            connector.setHuman(bandit);
            connector.threaten(defenders.getMembers()[(int) (Math.random() * (attackers.getMembers().length))]);
        }
    }

    public void robbery(int money, MoneyBag bag) throws EmptyRobberyException {
        connector.setHuman(getVictims().getMembers()[getVictims().getMembers().length - 1]);
        connector.openCashRegister(getBandits().getMembers()[0], money, bag);
        connector.setHuman(getBandits().getMembers()[0]);
        connector.threaten(getVictims().getMembers()[getVictims().getMembers().length - 1]);
        connector.setHuman(getVictims().getMembers()[getVictims().getMembers().length - 1]);
        connector.hide("Closet");
    }

    public void shooting(Team attackers, Team defenders) {
        System.out.println(attackers.getName() + " attack " + defenders.getName());
        for (Human a : attackers.getMembers()) {
            Human attacker = attackers.getMembers()[(int) (Math.random() * (attackers.getMembers().length))];
            Human defender = defenders.getMembers()[(int) (Math.random() * (defenders.getMembers().length))];
            connector.setHuman(attacker);
            connector.shoot(defender);
            if (defender.getState() == State.DEAD) {
                ++dead_counter;
            } else if (defender.getState() == State.INJURED) {
                ++injured_counter;
            }
            connector.setHuman(defender);
            connector.shoot(attacker);
            if (attacker.getState() == State.DEAD) {
                ++dead_counter;
            } else if (attacker.getState() == State.INJURED) {
                ++injured_counter;
            }
        }
    }

    public void addTeam(Team team, Building building) {
        team.goTo(building);
    }

    public void robberyEnd() {
        System.out.println("Robbery ended. Casualties: \nDead: " + dead_counter + "\nInjured: " + injured_counter);
    }

    public void escape(Team escaping_team) {
        escaping_team.goTo(null);
    }

    public Team getVictims() {
        return victims;
    }

    public Team getBandits() {
        return bandits;
    }

    public Team getPolice() {
        return police;
    }


}
