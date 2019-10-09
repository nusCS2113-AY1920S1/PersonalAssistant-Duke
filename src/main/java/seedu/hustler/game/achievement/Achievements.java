package seedu.hustler.game.achievement;


import java.time.LocalDateTime;

/**
 * Achievements that the user can achieve in Hustler.
 */
public abstract class Achievements implements Write{

    public static int totalPoints;


    public abstract String getAchievementLevel();

    public abstract String setStatus(String status);

    public abstract String getDescription();

    public abstract String getInformation();

    public abstract int getPoints();

    public abstract int setPoints(int points);

    public abstract Boolean checkLock();

    public abstract Boolean setLock(Boolean lock);

    /**
     *
     * @return
     */
    public String toString(){
        return "Gained:";
    }

}
