package Attacks;

import ru.ifmo.se.pokemon.*;

public class Growth extends StatusMove {
    public Growth() {
        super(Type.NORMAL, 0, 0);
    }

    @Override
    protected String describe() {
        return "растёт";
    }

    @Override
    protected void applySelfEffects(Pokemon p) {
        p.addEffect(new Effect().stat(Stat.ATTACK, (int) (Math.random() * 5 + 1)).stat(Stat.SPECIAL_ATTACK, (int) (Math.random() * 5 + 1)));
    }
}
