package BasicsForHumans;

import BasicsForBuildings.Building;
import Exceptions.NamelessObjectException;
import Interfaces.Storytelling;

public class Julio extends Human implements Storytelling {

    public Julio(String name, Role role) throws NamelessObjectException {
        super(name, role);
    }

    public void explain(Building building) throws NamelessObjectException {
        System.out.println(this.getName() + " explains the story");
        RobberyManager event = new RobberyManager();
        event.preparation(building, 40);
        event.shooting(event.getBandits(), event.getVictims());
        event.addTeam(event.getPolice(), building);
        event.shooting(event.getBandits(), event.getPolice());
        event.robberyEnd();
        System.out.println("That's what happened in the " + building.getName() + " that day.");

    }
}
