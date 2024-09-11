package Pokemons;
import Attacks.*;
import ru.ifmo.se.pokemon.*;

public class Tynamo extends Pokemon {
    public Tynamo(final String name, final int level) {
        super(name, level);
        setStats(35, 55, 40, 45, 40, 60);
        addType(Type.ELECTRIC);
        addMove(new ThunderWave());
        addMove(new ChargeBeam());
    }
}
