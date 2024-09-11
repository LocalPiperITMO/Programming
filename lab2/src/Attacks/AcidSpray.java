package Attacks;

import ru.ifmo.se.pokemon.*;

public class AcidSpray extends SpecialMove {
    public AcidSpray() {
        super(Type.POISON, 40, 100);

    }

    @Override
    protected String describe() {
        return "плюётся кислотой";
    }

    @Override
    protected void applyOppEffects(Pokemon p) {
        p.addEffect(new Effect().stat(Stat.SPECIAL_DEFENSE, (int) (Math.random() * 5 - 2)));
    }
}
