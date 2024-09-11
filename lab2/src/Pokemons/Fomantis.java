package Pokemons;

import Attacks.*;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class Fomantis extends Pokemon {
    public Fomantis(final String name, final int level) {
        super(name, level);
        setStats(40, 55, 35, 50, 35, 35);
        addType(Type.GRASS);
        addMove(new SweetScent());
        addMove(new Growth());
        addMove(new PoisonJab());
    }

}
