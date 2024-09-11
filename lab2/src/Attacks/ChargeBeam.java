package Attacks;

import ru.ifmo.se.pokemon.*;

public class ChargeBeam extends SpecialMove {
    public ChargeBeam() {
        super(Type.ELECTRIC, 50, 90);
    }

    @Override
    protected String describe() {
        return "стреляет!";
    }

    @Override
    protected void applySelfEffects(Pokemon p) {
        if ((int) (Math.random() * 100) <= 70) {
            p.addEffect(new Effect().stat(Stat.SPECIAL_ATTACK, (int) (Math.random() * 5 + 1)));
        }
    }
}
