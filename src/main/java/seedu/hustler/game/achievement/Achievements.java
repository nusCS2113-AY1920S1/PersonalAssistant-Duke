package seedu.hustler.game.achievement;

/**
 * Achievements that the user can achieve in Hustler.
 */
public abstract class Achievements implements Write {
    /**
     * Total points the user has.
     */
    public static int totalPoints;

    /**
     * Retrieve achievement level of a particular achievement.
     * @return
     */
    public abstract String getAchievementLevel();

    /**
     * Retrieve description of an achievement.
     * @return
     */
    public abstract String getDescription();

    /**
     * Retrieve information regarding an achievement.
     * @return
     */
    public abstract String getInformation();

    /**
     * Retrieve points attainable from an achievement.
     * @return
     */
    public abstract int getPoints();

    /**
     * Update how much point is gained from unlocking an achievement.
     * @param points number of points
     * @return
     */
    public abstract int setPoints(int points);

    /**
     * Check whether achievement is locked.
     * @return
     */
    public abstract Boolean checkLock();

    /**
     * Unlock an achievement.
     * @param lock lock
     * @return
     */
    public abstract Boolean setLock(Boolean lock);

    /**
     * Format which the achievement will be printed out.
     * @return
     */
    public String toString() {
        return "Gained:";
    }
}
