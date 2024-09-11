package Attacks;

import ru.ifmo.se.pokemon.Effect;
import ru.ifmo.se.pokemon.PhysicalMove;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class PoisonJab extends PhysicalMove {
    public PoisonJab() {
        super(Type.POISON, 80, 100);
    }

    @Override
    protected String describe() {
        return "использует тентакли";
    }

    @Override
    protected void applyOppEffects(Pokemon p) {
        if ((int) (Math.random() * 100) <= 30) {
            Effect.poison(p);
        }
    }
}
