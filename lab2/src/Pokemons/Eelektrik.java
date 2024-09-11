package Pokemons;

import Attacks.AcidSpray;
import Pokemons.Tynamo;

public class Eelektrik extends Tynamo {
    public Eelektrik(final String name, final int level) {
        super(name, level);
        setStats(65, 85, 70, 75, 70, 40);
        addMove(new AcidSpray());
    }
}
