package seedu.hustler.game.achievement;

import java.time.Duration;
import java.time.LocalDateTime;

public class ConsecutiveLogin extends Achievements {
    /**
     * Checks if user logs in consecutively.
     */
    public static Boolean consecutiveCheck;

    /**
     * Keeps track of how many times user logs in consecutively.
     */
    public static int consecutiveCount;

    /**
     * Points earned from logging in consecutively.
     */
    public static int loginPoints;

    /**
     * Date and time stored every time user logs in.
     */
    public static LocalDateTime storedDateTime;

    /**
     * Achievement level of achievement - Bronze, Silver, Gold.
     */
    public static String loginAchievementLevel;

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
    public ConsecutiveLogin(String achievementLevel) {
        this.description = "Dedicated to the art";
        this.information = "Logs in for 5, 10 and 15 days for Bronze, Silver and Gold consecutively.";
        this.achievementLevel = achievementLevel;
        this.points = 0;
        this.locked = true;
    }

    /**
     * Checks if user logs in consecutively day after day.
     * @return true if user logs in consecutively.
     */
    public static Boolean checkLogin() {

        LocalDateTime currentTime = LocalDateTime.now();
        long duration = Duration.between(storedDateTime,currentTime).toDays();
        if(duration == 1) {
            consecutiveCheck = true;
        } else {
            consecutiveCheck = false;
        }
        return consecutiveCheck;
    }

    /**
     * Resets consecutive count if user do not log in consecutively.
     * @return false if user did not log in consecutively.
     */
    public static Boolean reset() {
        LocalDateTime currentTime = LocalDateTime.now();
        long duration = Duration.between(storedDateTime,currentTime).toMinutes();
        if(duration > 1) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Update number of consecutive counts depending on how the user logs in.
     * @return consecutive counts.
     */
    public static int updateCount() {
        if(!checkLogin() && reset()) {
            consecutiveCount = 0;
        } else if(checkLogin()) {
            consecutiveCount ++;
        }
        return consecutiveCount;
    }

    /**
     * Update the achievement level if user meets the condition.
     * Condition base on number of consecutive login.
     * @return Achievement level.
     */
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

    /**
     * Update points accordingly.
     * @return
     */
    public static int updatePoints() {
        if(consecutiveCount == 5) {
            loginPoints = BRONZE_POINT;
            totalPoints += loginPoints;
        } else if(consecutiveCount == 10) {
            totalPoints += loginPoints;
            loginPoints += SILVER_POINT;
        } else if(consecutiveCount == 15) {
            totalPoints += loginPoints;
            loginPoints += GOLD_POINT;
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
