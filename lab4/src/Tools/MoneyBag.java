package Tools;

import Exceptions.EmptyRobberyException;

public class MoneyBag {
    private int money;

    public MoneyBag() {
        this.money = 0;
    }

    public void setMoney(int money) throws EmptyRobberyException {
        try {
            if (money != 0) {
                this.money = money;
            } else {
                throw new EmptyRobberyException("Robbers didn't take anything from the cash register!");
            }

        } catch (EmptyRobberyException no_money) {
            System.out.println("Unfortunately for bandits, cash register was empty");
        }
    }
}
