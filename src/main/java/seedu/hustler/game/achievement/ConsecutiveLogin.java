package seedu.hustler.game.achievement;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Achievement which can be attained after user logs in consecutively.
 * There is 3 achievement level which depending on the number of consecutive login.
 */
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
     * Achievement level - Bronze, Silver, Gold.
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
            loginAchievementLevel = "Bronze";
        } else if(consecutiveCount == 10) {
            loginAchievementLevel = "Silver";
        } else if (consecutiveCount == 15) {
            loginAchievementLevel = "Gold";
        } else {
            loginAchievementLevel = null;
        }
        return loginAchievementLevel;
    }

    /**
     * Update points accordingly.
     * @return points attained from consecutive login.
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
        } else {
            loginPoints = 0;
        }
        return loginPoints;
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
    public String getDescription() { return this.description; }

    /**
     * Retrieve information regarding the achievement.
     * @return information.
     */
    @Override
    public String getInformation() { return this.information; }

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
    public int setPoints(int points) {
        this.points = points;
        return points;
    }

    /**
     * Checks whether achievement have been unlocked.
     * @return true or false.
     */
    @Override
    public Boolean checkLock() { return locked; }

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
