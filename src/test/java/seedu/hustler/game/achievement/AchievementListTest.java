package seedu.hustler.game.achievement;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class for achievement list.
 */
public class AchievementListTest {

    /**
     * Checks if all achievements other than "fresh off the boat" are locked.
     */
    @Test
    public void checkAchievementLockStatus() {
        //checks if all achievements other than "fresh off the boat" is locked
        for(int i = 0; i < new AchievementList().size() - 1; i += 1) {
            assertEquals(true, new AchievementList().get(i).checkLock());
        }
        //checks if "fresh off the boat" is unlocked from the start
        assertEquals(false, new AchievementList().get(9).checkLock());
    }

    /**
     * Checks if the number of achievement available is correct.
     */
    @Test
    public void checkFixedAchievementListSize() { assertEquals(10,new AchievementList().size()); }

}
