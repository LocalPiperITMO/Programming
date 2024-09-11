package Interfaces;

import BasicsForBuildings.Building;
import Exceptions.EmptyRobberyException;
import Exceptions.NamelessObjectException;

public interface Storytelling {
    void explain(Building building) throws EmptyRobberyException, NamelessObjectException;
}
