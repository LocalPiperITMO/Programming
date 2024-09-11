package Attacks;

import ru.ifmo.se.pokemon.*;

public class Swagger extends StatusMove {
    public Swagger() {
        super(Type.NORMAL, 0, 85);
    }

    @Override
    protected String describe() {
        return "запутывает";
    }

    @Override
    protected void applyOppEffects(Pokemon p) {
        p.confuse();
        p.addEffect(new Effect().stat(Stat.ATTACK, (int) (Math.random() * 5 + 2)));
    }
}
