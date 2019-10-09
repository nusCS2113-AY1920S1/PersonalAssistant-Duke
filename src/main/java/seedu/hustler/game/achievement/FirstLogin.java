package seedu.hustler.game.achievement;

public class FirstLogin extends Achievements{

    /**
     * Description of achievement.
     */
    private String description;

    /**
     * Information regarding the achievement.
     */
    private String information;

    /**
     * Lock or unlock. If unlock, the level of the achievement.
     */
    private String achievementLevel;

    /**
     * Points earned.
     */
    private int points;


    private Boolean locked;

    /**
     * Initialise this achievement.
     */
    public FirstLogin() {
        this.description = "Fresh off the boat";
        this.information = "Use Hustler for the first time.";
        this.achievementLevel = "\uD83E\uDD47 Gold";
        this.points = 15;
        locked = false;
    }

    public static int updatePoints() {
        totalPoints += 15;
        return totalPoints;
    }

    @Override
    public String getAchievementLevel() {
        return achievementLevel;
    }

    @Override
    public String setStatus(String status) {
        this.achievementLevel = status;
        return this.achievementLevel;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getInformation() {
        return information;
    }

    @Override
    public int getPoints() {
        return points;
    }

    public int setPoints(int points) {
        this.points = points;
        return points;
    }

    @Override
    public Boolean checkLock() {
        return locked;
    }

    @Override
    public Boolean setLock(Boolean lock) {
        locked = lock;
        return locked;
    }

    @Override
    public String toString() {
        return super.toString() + " " + points + " " + this.description + " " + achievementLevel + " (" + this.information + ")";
    }

    @Override
    public String toTxt() {
        return locked + "|" + points + "|" + achievementLevel + "|" + description + "|" + information;
    }
}
