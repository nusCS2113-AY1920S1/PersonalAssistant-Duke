package seedu.hustler.game.achievement;

/**
 * Class of achievement which can be attained after certain
 * number of tasks are completed.
 * There is 3 achievement level which depends
 * on the number of tasks completed.
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
     *
     */
    public static String doneAchievementLevel;

    /**
     *
     */
    private String description;

    /**
     *
     */
    private String information;

    /**
     *
     */
    private String achievementLevel;

    /**
     *
     */
    private int points;

    /**
     *
     */
    private Boolean locked;

    /**
     *
     */
    private static final int BRONZE_POINT = 5;

    /**
     *
     */
    private static final int SILVER_POINT = 10;

    /**
     *
     */
    private static final int GOLD_POINT = 15;

    /**
     *
     * @param achievementLevel
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
        numberOfDone ++;
        return numberOfDone;
    }

    /**
     *
     * @return
     */
    public static String updateAchievementLevel() {

        if(numberOfDone == 5) {
            doneAchievementLevel = "\uD83E\uDD49 Bronze";
        } else if(numberOfDone == 25) {
            doneAchievementLevel = "\uD83E\uDD48 Silver";
        } else if (numberOfDone == 125) {
            doneAchievementLevel = "\uD83E\uDD47 Gold";
        }
        return doneAchievementLevel;
    }

    /**
     *
     * @return
     */
    public static int updatePoints() {
        if(numberOfDone == 5) {
            donePoints = BRONZE_POINT;
            totalPoints += donePoints;
        } else if(numberOfDone == 10) {
            donePoints = SILVER_POINT;
            totalPoints += donePoints;
        } else if (numberOfDone == 15) {
            totalPoints += donePoints;
            donePoints = GOLD_POINT;
        }
        return donePoints;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return super.toString() + " " + points + " " + description + " " + achievementLevel;
    }

    /**
     *
     * @return
     */
    @Override
    public String getDescription() { return description; }

    /**
     *
     * @return
     */
    @Override
    public String getAchievementLevel() {
        return this.achievementLevel;
    }

    /**
     *
     * @return
     */
    @Override
    public String getInformation() { return this.information; }

    /**
     *
     * @return
     */
    @Override
    public String toTxt() { return locked + "|" + points + "|" + achievementLevel + "|" + description + "|" + information; }

    /**
     *
     * @return
     */
    @Override
    public int getPoints() { return points; }

    /**
     *
     * @param points
     * @return
     */
    @Override
    public int setPoints(int points) {
        this.points = points;
        return points;
    }

    /**
     *
     * @return
     */
    @Override
    public Boolean checkLock() { return this.locked; }

    /**
     *
     * @param lock
     * @return
     */
    @Override
    public Boolean setLock(Boolean lock) {
        locked = lock;
        return locked;
    }
}
