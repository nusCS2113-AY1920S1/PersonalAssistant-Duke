package seedu.hustler.game.avatar;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 *  Test class for Level.
 */
public class LevelTest {

    /**
     * Checks if a newly constructed avatar has the correct Level and Xp.
     */
    @Test
    public void checkNewLevelTest() {
        Level levelTest = new Level();

        // Level and Xp should be 1 and 0 respectively for it to be true.
        assertEquals(1, levelTest.getLevel());
        assertEquals(0, levelTest.getXp());
    }

    /**
     * Checks if the gain xp function is working properly as intended.
     */
    @Test
    public void checkXpGainTest() {
        Level levelTest = new Level();

        // Should increment the xp by 1.
        levelTest = levelTest.increaseXp();
        assertEquals(1, levelTest.getXp());
    }

    /**
     * Checks if the boolean function to determine if the Level can level up
     * is working as intended.
     */
    @Test
    public void checkCanLevelTest() {
        Level levelTest = new Level();

        // New Level instance should not be able to level.
        assertFalse(levelTest.canLevel());

        // Level reaches threshold to level up should return true.
        Level levelUpTest = new Level(1, 5);
        assertTrue(levelUpTest.canLevel());
    }

    /**
     * Tests different instances of Level to determine if they are
     * the same as one another.
     */
    @Test
    public void equals() {
        Level level1 = new Level();
        Level level2 = new Level();

        // Same object should return true.
        assertEquals(level1, level1);

        // Same values should return true.
        assertEquals(level1, level2);

        // Different values of different levels should return false.
        level2 = level2.increaseXp();
        assertNotEquals(level1, level2);
    }

    /**
     * Checks if the toTxt format is working as intended.
     */
    @Test
    public void checkPrintingToTxt() {
        Level level1 = new Level();
        Level level2 = new Level(3, 14);
        Level level3 = new Level(4, 17);

        // The string should represent the level's current Level and Xp respectively, separated by a space.
        assertEquals("1 0", level1.toTxt());
        assertEquals("3 14", level2.toTxt());
        assertEquals("4 17", level3.toTxt());

        // Different levels with different values should not return the same toTxt format.
        assertNotEquals(level1.toTxt(), level2.toTxt());
    }

    @Test
    public void immutabilityCheck() {
        Level newLevel = new Level();

        // Should return the same level instance as before.
        newLevel.increaseXp().increaseXp().upLevel();
        assertEquals(newLevel, new Level());

        // Supports chaining when creating a new instance.
        Level upgradedLevel = new Level(1, 3);
        assertEquals(upgradedLevel, new Level().increaseXp().increaseXp().increaseXp());

    }



}
