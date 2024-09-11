package Attacks;

import ru.ifmo.se.pokemon.*;

public class MetalSound extends StatusMove {
    public MetalSound() {
        super(Type.STEEL, 0, 85);
    }

    @Override
    protected String describe() {
        return "кричит";
    }

    @Override
    protected void applyOppEffects(Pokemon p) {
        p.addEffect(new Effect().stat(Stat.SPECIAL_DEFENSE, (int) (Math.random() * 5 - 2)));
    }
}
