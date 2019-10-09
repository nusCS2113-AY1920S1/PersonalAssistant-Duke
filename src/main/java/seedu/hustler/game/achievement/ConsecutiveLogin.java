package seedu.hustler.game.achievement;

import java.time.Duration;
import java.time.LocalDateTime;

public class ConsecutiveLogin extends Achievements {

    public static Boolean consecutiveCheck;

    public static int consecutiveCount;

    public static int loginPoints;

    public static LocalDateTime storedDate;

    public static String loginAchievementLevel;

    private String description;

    private String information;

    private String achievementLevel;

    private int points;

    private Boolean locked;

    private static final int BRONZE_POINT = 5;

    private static final int SILVER_POINT = 10;

    private static final int GOLD_POINT = 15;


    public ConsecutiveLogin(String achievementLevel) {
        this.description = "Dedicated to the art";
        this.information = "Logs in for 5, 10 and 15 days for Bronze, Silver and Gold consecutively.";
        this.achievementLevel = achievementLevel;
        this.points = 0;
        this.locked = true;
    }


    public static Boolean checkLogin() {

        LocalDateTime currentTime = LocalDateTime.now();

        long duration = Duration.between(storedDate,currentTime).toMinutes();
        if(duration == 1) {
            consecutiveCheck = true;
        } else {
            consecutiveCheck = false;
        }
        return consecutiveCheck;
    }

    public static Boolean reset() {
        LocalDateTime currentTime = LocalDateTime.now();
        long duration = Duration.between(storedDate,currentTime).toMinutes();
        if(duration > 1) {
            return true;
        } else {
            return false;
        }
    }

    public static int updateCount() {

        if(checkLogin()) {
            consecutiveCount++;
        } else {
            consecutiveCount = 0;
        }

        return consecutiveCount;
    }

    public static String updateAchievementLevel() {
        if(consecutiveCount == 5) {
            loginAchievementLevel = "\uD83E\uDD49 Bronze";
        } else if(consecutiveCount == 10) {
            loginAchievementLevel = "\uD83E\uDD48 Silver";
        } else if (consecutiveCount == 15) {
            loginAchievementLevel = "\uD83E\uDD47 Gold";
        }
        return loginAchievementLevel;
    }

    public static int updatePoints() {
        if(consecutiveCount == 5) {
            loginPoints = BRONZE_POINT;
        } else if(consecutiveCount == 10) {
            loginPoints = SILVER_POINT;
        } else if(consecutiveCount == 15) {
            loginPoints = GOLD_POINT;
        }
        return loginPoints;
    }

    @Override
    public String toString() {
        return super.toString() + " " + points + " " + description + " " + achievementLevel;
    }

    @Override
    public String getDescription() { return this.description; }

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
        return locked + "|" + points + "|" + achievementLevel + "|" + description + "|" + information;
    }
}
