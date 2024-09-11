package Pokemons;

import Attacks.*;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class Durant extends Pokemon {
    public Durant(final String name, final int level) {
        super(name, level);
        setStats(58, 109, 112, 48, 48, 109);
        addType(Type.BUG);
        addType(Type.STEEL);
        addMove(new AerialAce());
        addMove(new RockSlide());
        addMove(new Swagger());
        addMove(new MetalSound());
    }
}
