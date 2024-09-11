package Pokemons;

import Attacks.AerialAce;

public class Lurantis extends Fomantis {
    public Lurantis(final String name, final int level) {
        super(name, level);
        setStats(70, 105, 90, 80, 90, 45);
        addMove(new AerialAce());
    }
}
