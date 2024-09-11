import Pokemons.*;
import ru.ifmo.se.pokemon.Battle;

public class Main {


    public static void main(String[] args) {
        Battle b = new Battle();
        Durant p1 = new Durant("p1", 10);
        Fomantis p2 = new Fomantis("p2", 10);
        Lurantis p3 = new Lurantis("p3", 10);
        Tynamo p4 = new Tynamo("p4", 10);
        Eelektrik p5 = new Eelektrik("p5", 10);
        Eelektross p6 = new Eelektross("p6", 10);
        b.addAlly(p1);
        b.addAlly(p2);
        b.addAlly(p3);
        b.addFoe(p4);
        b.addFoe(p5);
        b.addFoe(p6);
        b.go();
    }
}