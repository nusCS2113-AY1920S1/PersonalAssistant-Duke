package seedu.hustler.game.achievement;

public class AddTask extends Achievements {

    /**
     * Number of tasks added.
     */
    public static int numberOfTasks;

    public static int addPoints;

    public static String addAchievementLevel;

    private String description;

    private String information;

    private String achievementLevel;

    private int points;

    private Boolean locked;

    private static final int BRONZE_POINT = 5;

    private static final int SILVER_POINT = 10;

    private static final int GOLD_POINT = 15;


    public AddTask(String achievementLevel) {
        this.description = "Busybee";
        this.information = "Added 10 tasks, 100 tasks, and 1000 tasks for Bronze, Silver and Gold consecutively.";
        this.achievementLevel = achievementLevel;
        this.points = 0;
        this.locked = true;
    }

    /**
     * Increase the number of tasks added.
     * @return number of tasks added.
     */
    public static int increment() {
        numberOfTasks++;
        return numberOfTasks;
    }

    public static String updateAchievementLevel() {
        if(numberOfTasks == 5) {
            addAchievementLevel = "\uD83E\uDD49 Bronze";
        } else if(numberOfTasks == 10) {
            addAchievementLevel = "\uD83E\uDD48 Silver";
        } else if (numberOfTasks == 15) {
            addAchievementLevel = "\uD83E\uDD47 Gold";
        }
        return addAchievementLevel;
    }

    public static int updatePoints() {
        if(numberOfTasks == 5) {
            addPoints = BRONZE_POINT;
            totalPoints += addPoints;
        } else if(numberOfTasks == 10) {
            addPoints = SILVER_POINT;
            totalPoints += addPoints;
        } else if (numberOfTasks == 15) {
           addPoints = GOLD_POINT;
           totalPoints += addPoints;
        }
        return addPoints;
    }

    @Override
    public String toString() {
        return super.toString() + " " + points + " " + description + " " + achievementLevel;
    }

    @Override
    public String getAchievementLevel() { return this.achievementLevel; }

    @Override
    public String setStatus(String status) {
        this.achievementLevel = status;
        return this.achievementLevel;
    }

    @Override
    public String getDescription() { return description; }

    @Override
    public String getInformation() { return this.information; }

    @Override
    public int getPoints() { return points; }

    @Override
    public int setPoints(int points) {
        this.points = points;
        return points;
    }

    @Override
    public Boolean checkLock() { return locked; }

    public Boolean setLock(Boolean lock) {
        locked = lock;
        return locked;
    }

    @Override
    public String toTxt() {
        return locked + "|" + points + "|" + achievementLevel + "|" + description + "|" + information;
    }
}
