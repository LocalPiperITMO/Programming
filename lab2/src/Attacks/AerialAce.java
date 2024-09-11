package Attacks;

import ru.ifmo.se.pokemon.*;

public class AerialAce extends PhysicalMove {
    public AerialAce() {
        super(Type.FLYING, 60, 0);
    }

    @Override
    protected String describe() {
        return "запутывает цель большой скоростью, а затем атакует";
    }

    @Override
    protected boolean checkAccuracy(Pokemon att, Pokemon def) {
        return true;
    }
}
