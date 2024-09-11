public class RobberyManager {
    private final Building building;
    private Team bandits;
    private Team victims;
    private Team police;

    private int attackers_dead = 0;
    private int attackers_injured = 0;
    private int defenders_dead = 0;
    private int defenders_injured = 0;
    private int victims_dead = 0;
    private int victims_injured = 0;
    private final int number_of_bandits;
    private final int number_of_workers;
    private final int number_of_policemen;

    public RobberyManager(Building building) {
        this.building = building;
        this.number_of_bandits = (int) (Math.random() * 40);
        this.number_of_workers = (int) (Math.random() * 40);
        this.number_of_policemen = (int) (Math.random() * 40);
    }

    public void preparation() {
        bandits = new Team("Gang of Bandits", Role.BANDIT);
        victims = new Team("Workers of " + this.building.getName(), this.building.getRole());
        police = new Team("Police", Role.POLICEMAN);
        for (int i = 1; i < this.number_of_bandits; ++i) {
            bandits.addToTeam(Bandit.initBandit(new Human(String.format("Bandit %d", i), Role.BANDIT)));
        }
        for (int i = 1; i < this.number_of_workers; ++i) {
            if (this.building.getRole() == Role.BANKER) {
                Banker member = Banker.initBanker(new Human(String.format("Banker %d", i), this.building.getRole()));
                victims.addToTeam(member);
            } else if (this.building.getRole() == Role.AUCTIONEER) {
                Auctioneer member = Auctioneer.initAuctioneer(new Human(String.format("Auctioneer %d", i), this.building.getRole()));
                victims.addToTeam(member);
            }

        }

        for (int i = 1; i < this.number_of_policemen; ++i) {
            police.addToTeam(Policeman.initPoliceman(new Human(String.format("Policeman %d", i), Role.POLICEMAN)));
        }
        System.out.println(victims.getName() + " were at the " + this.building.getName() + " when " + bandits.getName() + " appeared!");
        bandits.goTo(this.building);
        Cashier cashier = Cashier.initCashier(new Human("Cashier", Role.CASHIER));
        cashier.hide("Safe");
        victims.addToTeam(cashier);
    }

    public void robberyStart() {
        System.out.println("Robbery started!");
        shooting(bandits, victims);
        newTeamArrival(police);
        shooting(bandits, police);
    }

    public void shooting(Team attacking_team, Team defending_team) {
        System.out.println(attacking_team.getName() + " attacked " + defending_team.getName() + "!");
        if (defending_team.getMembers()[0] instanceof Unarmed) {
            for (int i = 0; i < Math.min(attacking_team.getMembers().length, defending_team.getMembers().length); ++i) {
                Bandit attacker = (Bandit) attacking_team.getMembers()[(int) (Math.random() * attacking_team.getMembers().length)];
                Unarmed victim = (Unarmed) defending_team.getMembers()[(int) (Math.random() * defending_team.getMembers().length)];
                attacker.shoot(victim);
                if (victim.getState() == State.DEAD) {
                    ++victims_dead;
                } else if (victim.getState() == State.INJURED) {
                    ++victims_injured;
                }
            }
        } else {
            for (int i = 0; i < Math.min(attacking_team.getMembers().length, defending_team.getMembers().length); ++i) {
                Bandit attacker = (Bandit) attacking_team.getMembers()[(int) (Math.random() * attacking_team.getMembers().length)];
                Policeman defender = (Policeman) defending_team.getMembers()[(int) (Math.random() * defending_team.getMembers().length)];
                attacker.shoot(defender);
                defender.shoot(attacker);
                if (attacker.getState() == State.DEAD) {
                    ++attackers_dead;
                } else if (attacker.getState() == State.INJURED) {
                    ++attackers_injured;
                }
                if (defender.getState() == State.DEAD) {
                    ++defenders_dead;
                } else if (defender.getState() == State.INJURED) {
                    ++defenders_injured;
                }
            }
        }
    }

    public void newTeamArrival(Team new_team) {
        System.out.println("After some time, " + new_team.getName() + " arrived!");
        new_team.goTo(this.building);
    }

    public void robberyEnd() {
        System.out.println("Shooting finally ended. Casualties: \nBandits dead: " + attackers_dead + "\nBandits injured: " + attackers_injured);
        System.out.println("Policemen dead: " + defenders_dead + "\nPolicemen injured: " + defenders_injured);
        System.out.println("Workers dead: " + victims_dead + "\nWorkers injured: " + victims_injured);
    }
}
