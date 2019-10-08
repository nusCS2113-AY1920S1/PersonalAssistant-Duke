package seedu.hustler.game.achievement;

import java.time.LocalDateTime;

public class ConsecutiveLogin extends Achievements {

    public static int loginCount;

    public static int loginPoints;

    public static String loginAchievementLevel;

    private String description;

    private String information;

    private String achievementLevel;

    private int points;

    private Boolean locked;

    private LocalDateTime localDateTime;

    private static final int BRONZE_POINT = 5;

    private static final int SILVER_POINT = 10;

    private static final int GOLD_POINT = 15;

    public ConsecutiveLogin(String achievementLevel) {
        this.description = "Dedicated to the art.";
        this.information = "Logs in for 5, 10 and 15 days for Bronze, Silver and Gold consecutively.";
        this.achievementLevel = achievementLevel;
        this.points = 0;
        this.locked = true;
    }

    @Override
    public String toString() {
        return super.toString() + points + " Completionist.";
    }

    @Override
    public String getDescription() { return null; }

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
    public int getPoints() { return points; }

    public int setPoints(int points) {
        this.points = points;
        return points;
    }

    @Override
    public Boolean checkLock() { return locked; }

    @Override
    public Boolean setLock(Boolean lock) {
        locked = lock;
        return locked;
    }

    @Override
    public String toTxt() {
        return achievementLevel + "|" + description + "|" + information;
    }
}
