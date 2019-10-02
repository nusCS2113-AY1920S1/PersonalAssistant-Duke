package seedu.duke.avatar;

/**
 * A class for the avatar in Hustler.
 */
public class Avatar {

    /**
     * Level of the avatar
     */
    private Level level;

    /**
     * Stats of the avatar
     */
    private Stats stats; 

    /**
     * Default initialization of the level and stat.
     */
    public Avatar() {
        this.level = new Level();
        this.stats = new Stats();
    }

    /**
     * Initializing level and stat with specific
     * values.
     *
     * @param level object to initialize level with
     * @param stats object to initialize stats with
     */
    public Avatar(Level level, Stats stats) {
        this.level = level;
        this.stats = stats;
    }

}
