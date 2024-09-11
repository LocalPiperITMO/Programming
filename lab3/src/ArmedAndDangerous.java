public interface ArmedAndDangerous {
    String[] weapons = {"pistol", "rifle", "armored car", "grenade"};


    String getWeapon();

    void shoot(AbstractRole victim);
}
