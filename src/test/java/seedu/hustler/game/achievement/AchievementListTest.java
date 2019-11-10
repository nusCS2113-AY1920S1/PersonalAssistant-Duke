package seedu.hustler.game.achievement;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * Test class for achievement list.
 */
public class AchievementListTest {

    /**
     * Checks if all achievements other than "fresh off the boat" are locked.
     */
    @Test
    public void checkAchievementLockStatus() {

        AchievementList achievementList = new AchievementList();

        //Checks if all achievements other than "fresh off the boat" is locked
        for(int i = 0; i < achievementList.size() - 1; i += 1) {
            assertEquals(true, new AchievementList().get(i).checkLock());
        }
        //Checks if "fresh off the boat" is unlocked from the start
        assertEquals(false, achievementList.get(9).checkLock());

        achievementList.add(new AchievementsStub());
        //Checks if achievement is locked by default.
        assertEquals(true, achievementList.get(10).checkLock());
    }

    /**
     * Checks if the number of achievement available is correct.
     */
    @Test
    public void checkFixedAchievementListSize() {
        AchievementList fiftyAchievementsList = new AchievementList();
        for(int i = 0; i < 40; i += 1) {
            fiftyAchievementsList.add(new AchievementsStub());
        }
        assertEquals(50,fiftyAchievementsList.size());
    }

    /**
     * Checks that there is no duplicate of busybee achievement level bronze in achievement list.
     * Ensure that
     */
    @Test
    public void checkAchievementLevel() {
        AchievementList achievementList = new AchievementList();
        assertEquals(achievementList.get(0).getAchievementLevel(), new AchievementsStub().getAchievementLevel());
        //Silver busybee.
        assertNotEquals(achievementList.get(1).getAchievementLevel(), new AchievementsStub().getAchievementLevel());
        //Gold busybee.
        assertNotEquals(achievementList.get(2).getAchievementLevel(), new AchievementsStub().getAchievementLevel());
    }

    /**
     * Class of achievement to be used for testing out the different functions.
     */
    private class AchievementsStub implements Achievement {

        /**
         * Points attained.
         */
        int points = 0;

        /**
         * Current achievement level.
         * @return Bronze.
         */
        @Override
        public String getAchievementLevel() {
            return "Bronze";
        }

        /**
         * Description of achievement.
         * @return description of achievement.
         */
        @Override
        public String getDescription() {
            throw new AssertionError("This method should not be called.");
        }

        /**
         * Information on how to unlock achievement.
         * @return achievement information.
         */
        @Override
        public String getInformation() {
            throw new AssertionError("This method should not be called.");
        }

        /**
         * Get points attained from this achievement.
         * @return points.
         */
        @Override
        public int getPoints() {
            return this.points;
        }

        /**
         * Set points attained from unlocking this achievement.
         * @param points number of points.
         * @return points.
         */
        @Override
        public int setPoints(int points) {
            this.points = points;
            return this.points;
        }

        /**
         * Check if achievement is unlocked.
         * @return true or false.
         */
        @Override
        public Boolean checkLock() {
            return true;
        }

        /**
         * Unlock or lock achievement.
         * @param lock lock
         * @return true or false.
         */
        @Override
        public Boolean setLock(Boolean lock) {
            return true;
        }

        /**
         * Format to write to file.
         * @return correct format.
         */
        @Override
        public String toTxt() { throw new AssertionError("This method should not be called."); }
    }

}
