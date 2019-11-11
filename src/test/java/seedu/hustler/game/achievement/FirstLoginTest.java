package seedu.hustler.game.achievement;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

//@@author jingkang97

/**
 * Test for "Fresh off the boat".
 */
public class FirstLoginTest {

    /**
     * Checks if fresh off the boat can be unlock.
     */
    @Test
    public void checkUnlockFirstLogin() {
        AchievementsStub firstLogin = new AchievementsStub("Gold");
        firstLogin.setLock(false);
        assertEquals(false, firstLogin.checkLock());
    }

    /**
     * Checks if the condition for unlocking each achievement level is correct.
     */
    @Test
    public void checkFirstLoginInformation() {
        AchievementsStub gold = new AchievementsStub("Gold");

        //checks if the condition for getting bronze achievement level is correct
        assertEquals("(User use Hustler for the first time)", gold.getInformation());
    }

    /**
     * Checks if the toTxt format is working as intended.
     */
    @Test
    public void checkPrintingToTxt() {

        AchievementsStub unlockedGold = new AchievementsStub("Gold");

        //String should represents the current status of Busybee.
        assertEquals("false|15|Gold|Fresh off the boat|(User use Hustler for the first time) Progress: [100%]", unlockedGold.toTxt());

    }

    /**
     * Checks if the toString format is working as intended.
     */
    @Test
    public void checkPrintingToString() {

        AchievementsStub lockedGold = new AchievementsStub("Gold");

        //String should represents the current status of Busybee.
        assertEquals("Gained: 15 Fresh off the boat Gold(User use Hustler for the first time) Progress: [100%]", lockedGold.toString());
    }

    /**
     * Class of achievement to be used for testing out the different functions.
     */
    private class AchievementsStub extends Achievements implements Achievement {

        /**
         * Achievement level.
         */
        String achievementLevel;

        /**
         * To lock achievement.
         */
        Boolean lock;

        /**
         * Points attained.
         */
        int points = 15;

        /**
         * Initialise achievement stub.
         * @param achievementLevel achievement level.
         */
        public AchievementsStub(String achievementLevel) {
            this.lock = false;
            this.achievementLevel = achievementLevel;
        }

        /**
         * This method will not be tested.
         * @return none.
         */
        @Override
        public String getAchievementLevel() {
            throw new AssertionError("This method should not be called.");
        }

        /**
         * Description of achievement.
         * @return description of achievement.
         */
        @Override
        public String getDescription() {
            return "Fresh off the boat";
        }

        /**
         * Information on how to unlock achievement.
         * @return achievement information.
         */
        @Override
        public String getInformation() {
            return "(User use Hustler for the first time)";
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
            return this.lock;
        }

        /**
         * Unlock or lock achievement.
         * @param lock lock
         * @return true or false.
         */
        @Override
        public Boolean setLock(Boolean lock) {
            return this.lock = lock;
        }

        /**
         * Format to write to file.
         * @return correct format.
         */
        @Override
        public String toTxt() {
            return checkLock() + "|" + this.points + "|" + achievementLevel + "|" + getDescription() + "|" + getInformation() + " Progress: [100%]";
        }

        /**
         * Format to print to user.
         * @return message.
         */
        @Override
        public String toString() {
            return super.toString() + " 15 Fresh off the boat Gold(User use Hustler for the first time) Progress: [100%]";
        }
    }

}
