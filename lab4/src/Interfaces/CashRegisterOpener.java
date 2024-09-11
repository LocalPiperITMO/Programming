package Interfaces;

import BasicsForHumans.Human;
import Exceptions.EmptyRobberyException;
import Tools.MoneyBag;

public interface CashRegisterOpener {
    void openCashRegister(Human bandit, int money, MoneyBag bag) throws EmptyRobberyException;
}
