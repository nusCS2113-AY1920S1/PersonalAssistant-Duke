package seedu.hustler.game.achievement;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

//@@author jingkang97

/**
 * Test for "Completionist".
 */
public class CompletionistTest {

    /**
     * Checks if Completionist can be unlock.
     */
    @Test
    public void checkCompletionistBusyBee() {
        AchievementsStub doneTask = new AchievementsStub("Bronze");
        doneTask.setLock(false);
        assertEquals(false, doneTask.checkLock());
    }

    /**
     * Checks if the condition for unlocking each achievement level is correct.
     */
    @Test
    public void checkCompletionistInformation() {
        AchievementsStub bronze = new AchievementsStub("Bronze");
        AchievementsStub silver = new AchievementsStub("Silver");
        AchievementsStub gold = new AchievementsStub("Gold");

        //checks if the condition for getting bronze achievement level is correct
        assertEquals("(User completes 5 tasks)", bronze.getInformation());
        //checks if the condition for getting silver achievement level is correct
        assertEquals("(User completes 10 tasks)", silver.getInformation());
        //checks if the condition for getting gold achievement level is correct
        assertEquals("(User completes 15 tasks)", gold.getInformation());

        //checks if the condition for getting bronze achievement level is different from silver
        assertNotEquals(bronze.getInformation(),silver.getInformation());
    }

    /**
     * Checks if the toTxt format is working as intended.
     */
    @Test
    public void checkPrintingToTxt() {

        AchievementsStub lockedBronze = new AchievementsStub("Bronze");
        AchievementsStub unlockedBronze = new AchievementsStub("Bronze");

        unlockedBronze.setLock(false);
        unlockedBronze.setPoints(5);

        //String should represents the current status of Busybee.
        assertEquals("true|0|Bronze|Completionist|(User completes 5 tasks)", lockedBronze.toTxt());
        assertEquals("false|5|Bronze|Completionist|(User completes 5 tasks)", unlockedBronze.toTxt());

    }

    /**
     * Checks if the toString format is working as intended.
     */
    @Test
    public void checkPrintingToString() {

        AchievementsStub lockedBronze = new AchievementsStub("Bronze");

        //String should represents the current status of Busybee.
        assertEquals("Gained: 0 Completionist Bronze (User completes 5 tasks) Progress: [0%]", lockedBronze.toString());
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
        int points = 0;

        /**
         * Initialise achievement stub.
         * @param achievementLevel achievement level.
         */
        public AchievementsStub(String achievementLevel) {
            this.lock = true;
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
            return "Completionist";
        }

        /**
         * Information on how to unlock achievement.
         * @return achievement information.
         */
        @Override
        public String getInformation() {
            switch (this.achievementLevel) {
            case "Bronze" : {
                return "(User completes 5 tasks)";
            }
            case "Silver" : {
                return "(User completes 10 tasks)";
            }
            case "Gold" : {
                return "(User completes 15 tasks)";
            }
            default:
                break;
            }
            return "(User completes 0 tasks)";
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
            return checkLock() + "|" + this.points + "|" + achievementLevel
                    + "|" + getDescription() + "|" + getInformation();
        }

        /**
         * Format to print to user.
         * @return message.
         */
        @Override
        public String toString() {
            return super.toString() + " 0 Completionist Bronze (User completes 5 tasks) Progress: [0%]";
        }
    }


}
