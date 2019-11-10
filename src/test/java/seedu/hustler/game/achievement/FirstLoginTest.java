package seedu.hustler.game.achievement;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
    private class AchievementsStub extends Achievements implements Achievement {

        String achievementLevel;
        Boolean lock;
        int points = 15;

        public AchievementsStub(String achievementLevel) {
            this.lock = false;
            this.achievementLevel = achievementLevel;
        }

        @Override
        public String getAchievementLevel() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public String getDescription() {
            return "Fresh off the boat";
        }

        @Override
        public String getInformation() {
            return "(User use Hustler for the first time)";
        }

        @Override
        public int getPoints() {
            return this.points;
        }

        @Override
        public int setPoints(int points) {
            this.points = points;
            return this.points;
        }

        @Override
        public Boolean checkLock() {
            return this.lock;
        }

        @Override
        public Boolean setLock(Boolean lock) {
            return this.lock = lock;
        }

        @Override
        public String toTxt() {
            return checkLock() + "|" + this.points + "|" + achievementLevel + "|" + getDescription() + "|" + getInformation() + " Progress: [100%]";
        }

        @Override
        public String toString() {
            return super.toString() + " 15 Fresh off the boat Gold(User use Hustler for the first time) Progress: [100%]";
        }
    }

}
