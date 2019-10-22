package seedu.hustler.game.achievement;

/**
 * Achievement which can be attained after certain number of tasks are completed.
 * There is 3 achievement level which depends on the number of tasks completed.
 */
public class DoneTask extends Achievements {
    /**
     * Number of tasks done.
     */
    public static int numberOfDone;

    /**
     * Keeps track of final points which is dependent
     * on number of tasks completed and stores it.
     */
    public static int donePoints;

    /**
     * Achievement level - Bronze, Silver, Gold.
     */
    public static String doneAchievementLevel;

    /**
     * Description of achievement.
     */
    private String description;

    /**
     * Information regarding how to attain achievement.
     */
    private String information;

    /**
     * Achievement level - Bronze, Silver, Gold.
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
     * Points earn from bronze level of achievement.
     */
    private static final int BRONZE_POINT = 5;

    /**
     * Points earn from silver level achievement.
     */
    private static final int SILVER_POINT = 10;

    /**
     * Points from gold level achievement.
     */
    private static final int GOLD_POINT = 15;

    /**
     * Initialise this achievement.
     * @param achievementLevel Achievement level of the achievement.
     */
    public DoneTask(String achievementLevel) {
        this.description = "Completionist";
        this.information = "Completed 5 tasks, 25 tasks and 125 tasks for Bronze, Silver and Gold consecutively.";
        this.achievementLevel = achievementLevel;
        this.points = 0;
        this.locked = true;
    }

    /**
     * Increase number of tasks done when user mark task as done.
     * @return number of tasks done.
     */
    public static int increment() {
        numberOfDone++;
        return numberOfDone;
    }

    /**
     * Update the achievement level if user meets the condition.
     * Condition base on number of tasks done.
     * @return Achievement level.
     */
    public static String updateAchievementLevel() {
        if (numberOfDone == 5) {
            doneAchievementLevel = "\uD83E\uDD49 Bronze";
        } else if (numberOfDone == 10) {
            doneAchievementLevel = "\uD83E\uDD48 Silver";
        } else if (numberOfDone == 15) {
            doneAchievementLevel = "\uD83E\uDD47 Gold";
        }
        return doneAchievementLevel;
    }

    /**
     * Update points accordingly.
     * @return points attained from number of tasks done.
     */
    public static int updatePoints() {
        if (numberOfDone == 5) {
            donePoints = BRONZE_POINT;
            totalPoints += donePoints;
        } else if (numberOfDone == 10) {
            donePoints = SILVER_POINT;
            totalPoints += donePoints;
        } else if (numberOfDone == 15) {
            totalPoints += donePoints;
            donePoints = GOLD_POINT;
        }
        return donePoints;
    }

    /**
     * Gets achievement level of the achievement.
     * @return achievement level.
     */
    @Override
    public String getAchievementLevel() {
        return this.achievementLevel;
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
    public String getInformation() {
        return this.information;
    }

    /**
     * Retrieve current points from achievement.
     * @return points.
     */
    @Override
    public int getPoints() {
        return points;
    }

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
        return this.locked;
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
        return super.toString() + " " + points + " " + description + " " + achievementLevel;
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
