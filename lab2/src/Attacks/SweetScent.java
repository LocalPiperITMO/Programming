package Attacks;

import ru.ifmo.se.pokemon.*;

public class SweetScent extends StatusMove {
    public SweetScent(){
        super(Type.NORMAL,0,100);
    }
    @Override
    protected String describe(){
        return "приятно пахнет";
    }
    @Override
    protected void applyOppEffects(Pokemon p){
        p.addEffect(new Effect().stat(Stat.EVASION,(int)(Math.random()*5-1)));
    }
}
