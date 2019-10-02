package seedu.duke.avatar;

/**
 * Class that deals with the stats of avatar.
 */
public class Stats {
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
     * Default initialization of variables.
     */
    public Stats() {
        this.damage = 0;
        this.defence = 0;
        this.stamina = 0;
        this.speed = 0;
    }
}
