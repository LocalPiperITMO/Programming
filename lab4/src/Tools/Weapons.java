package Tools;

public enum Weapons {
    PISTOL(10, 30),
    ASSAULT_RIFLE(30, 30),
    GRENADE(20, 50),
    SNIPER_RIFLE(80, 20),
    ARMORED_CAR(60, 40),
    ROCKET_LAUNCHER(80, 40),
    GRENADE_LAUNCHER(70, 50),
    KNIFE(100, 100);
    private final int kill_chance;
    private final int hurt_chance;

    Weapons(int kill_chance, int hurt_chance) {
        this.kill_chance = kill_chance;
        this.hurt_chance = hurt_chance;
    }

    public int getKill_chance() {
        return this.kill_chance;
    }

    public int getHurt_chance() {
        return this.hurt_chance;
    }
}
