package seedu.hustler.game.achievement;

public class DoneTask extends Achievements {

    /**
     * Number of tasks done.
     */
    public static int numberOfDone;

    public static int donePoints;

    public static String doneAchievementLevel;

    private String description;

    private String information;

    private String achievementLevel;

    private int points;

    private Boolean locked;

    private static final int BRONZE_POINT = 5;

    private static final int SILVER_POINT = 10;

    private static final int GOLD_POINT = 15;

    public DoneTask(String achievementLevel) {
        this.description = "Completionist";
        this.information = "Completed 5 tasks, 25 tasks and 125 tasks for Bronze, Silver and Gold consecutively.";
        this.achievementLevel = achievementLevel;
        this.points = 0;
        locked = true;
    }

    /**
     * Increase number of tasks done when user mark task as done.
     * @return number of tasks done.
     */
    public static int increment() {
        numberOfDone ++;
        return numberOfDone;
    }

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

    @Override
    public String toString() {
        return super.toString() + " " + points + " " + description + " " + achievementLevel;
    }

    @Override
    public String getDescription() { return description; }

    @Override
    public String getAchievementLevel() {
        return this.achievementLevel;
    }

    @Override
    public String setStatus(String status) {
        this.achievementLevel = status;
        return this.achievementLevel;
    }

    @Override
    public String getInformation() { return this.information; }

    @Override
    public String toTxt() { return locked + "|" + points + "|" + achievementLevel + "|" + description + "|" + information; }

    @Override
    public int getPoints() { return points; }

    @Override
    public int setPoints(int points) {
        this.points = points;
        return points;
    }

    @Override
    public Boolean checkLock() { return this.locked; }

    @Override
    public Boolean setLock(Boolean lock) {
        locked = lock;
        return locked;
    }
}
