package seedu.hustler.game.achievement;

/**
 * Achievement which can be attained after user first logs in.
 *
 */
public class FirstLogin extends Achievements{

    /**
     * Description of achievement.
     */
    private String description;

    /**
     * Information regarding how to attain achievement.
     */
    private String information;

    /**
     * Achievement level of achievement - Bronze, Silver, Gold.
     */
    private String achievementLevel;

    /**
     * Points earned.
     */
    private int points;

    /**
     * Achievement status.
     */
    private Boolean locked;

    /**
     * Initialise this achievement.
     */
    public FirstLogin() {
        this.description = "Fresh off the boat";
        this.information = "Use Hustler for the first time.";
        this.achievementLevel = "Gold";
        this.points = 15;
        locked = false;
    }

    /**
     * Update total points when user attains this achievement.
     * @return total points.
     */
    public static int updatePoints() {
        totalPoints += 15;
        return totalPoints;
    }

    /**
     * Gets achievement level of the achievement.
     * @return achievement level.
     */
    @Override
    public String getAchievementLevel() {
        return achievementLevel;
    }

    /**
     * Retrieve description of achievement.
     * @return description.
     */
    @Override
    public String getDescription() {
        return description;
    }

    /**
     * Retrieve information regarding the achievement.
     * @return information.
     */
    @Override
    public String getInformation() { return information; }

    /**
     * Retrieve current points from achievement.
     * @return points.
     */
    @Override
    public int getPoints() { return points; }

    /**
     * Update points gained from unlocking achievement.
     * @param points updated points.
     * @return points.
     */
    @Override
    public int setPoints(int points) {
        this.points = points;
        return points;
    }

    /**
     * Checks whether achievement have been unlocked.
     * @return true or false.
     */
    @Override
    public Boolean checkLock() {
        return locked;
    }

    /**
     * Unlocks achievement.
     * @param lock Lock condition.
     * @return true or false.
     */
    @Override
    public Boolean setLock(Boolean lock) {
        locked = lock;
        return locked;
    }

    /**
     * The format in which the achievement will be printed out.
     * @return string format of the achievement.
     */
    @Override
    public String toString() {
        return super.toString() + " " + points + " " + this.description + " " + achievementLevel;
    }

    /**
     * Format in which achievement will be stored.
     * @return string format of achievement.
     */
    @Override
    public String toTxt() {
        return locked + "|" + points + "|" + achievementLevel + "|" + description + "|" + information;
    }
}
