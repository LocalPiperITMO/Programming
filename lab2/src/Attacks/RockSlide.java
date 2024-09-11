package Attacks;

import ru.ifmo.se.pokemon.*;

public class RockSlide extends PhysicalMove {
    public RockSlide() {
        super(Type.ROCK, 75, 90);
    }

    @Override
    protected String describe() {
        return "швыряется камнями";
    }

    @Override
    protected void applyOppEffects(Pokemon p) {
        if ((int) (Math.random() * 100) <= 30) {
            Effect.flinch(p);
        }
    }
}
