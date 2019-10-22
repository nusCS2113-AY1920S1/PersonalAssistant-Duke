package seedu.hustler.game.avatar;

/**
 * Class that deals with the stats of avatar.
 */
public class Stats implements Convertible {
    /**
     * Stat that deals damage.
     */
    private int damage;

    /**
     * Stat that blocks damage.
     */
    private int defence;

    /**
     * Stat that represents stamina.
     */
    private int stamina;

    /**
     * Stat that represents speed.
     */
    private int speed;

    /**
     * Constructs the default Stats values.
     */
    public Stats() {
        this.damage = 1;
        this.defence = 1;
        this.stamina = 3;
        this.speed = 1;
    }

    /**
     * Construct Stats with the given damage, defence, stamina
     * and speed.
     * @param damage the damage of the avatar.
     * @param defence the defence of the avatar.
     * @param stamina the stamina of the avatar.
     * @param speed the speed of the avatar.
     */
    public Stats(int damage, int defence, int stamina, int speed) {
        this.damage = damage;
        this.defence = defence;
        this.stamina = stamina;
        this.speed = speed;
    }

    /**
     * Increases the stats based on the current level.
     * @param level the current level of the avatar.
     * @return the updated stats after increment.
     */
    public Stats upStats(int level) {
        this.damage += (level % 2 == 0) ? 2 : 1;
        this.defence += (level % 3 == 0) ? 2 : 1;
        this.stamina += 2;
        this.speed += (level % 3 == 0) ? 1 : 0;
        return this;
    }

    @Override
    public String toString() {
        return "Damage: "  + this.damage + "\n"
            + "Defence: " + this.defence + "\n"
            + "Stamina: " + this.stamina + "\n"
            + "Speed: " + this.speed;
    }

    @Override
    public String toTxt() {
        return this.damage + " " + this.defence + " "
            + this.stamina + " " + this.speed;
    }
}
