package Tools;

import BasicsForBuildings.Building;
import BasicsForHumans.RobberyManager;
import Exceptions.EmptyRobberyException;
import Exceptions.NamelessObjectException;
import Interfaces.Storytelling;

public class Newspaper implements Storytelling {
    public void explain(Building building) throws EmptyRobberyException, NamelessObjectException {
        System.out.println("Newspapers state what really happened at " + building.getName() + " that day");
        RobberyManager event = new RobberyManager();
        event.preparation(building, 2);
        event.threatening(event.getBandits(), event.getVictims());
        event.robbery(building.getCash(), new MoneyBag());
        event.escape(event.getBandits());
    }
}
