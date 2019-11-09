package seedu.hustler.game.achievement;

/**
 * The interface that all classes in achievement must inherit to
 * convert them to proper format before storing them in a txtfile.
 */
public interface Achievement extends Write{


    /**
     * Retrieve achievement level of a particular achievement.
     * @return
     */
    String getAchievementLevel();

     /**
     * Retrieve description of an achievement.
     * @return
     */
    String getDescription();

     /**
     * Retrieve information regarding an achievement.
     * @return
     */
   String getInformation();

     /**
     * Retrieve points attainable from an achievement.
     * @return
     */
   int getPoints();

     /**
     * Update how much point is gained from unlocking an achievement.
     * @param points number of points
     * @return
     */
    int setPoints(int points);

     /**
     * Check whether achievement is locked.
     * @return
     */
   Boolean checkLock();

     /**
     * Unlock an achievement.
     * @param lock lock
     * @return
     */
   Boolean setLock(Boolean lock);

}
