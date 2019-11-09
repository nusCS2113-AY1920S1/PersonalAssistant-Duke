package seedu.hustler.game.avatar;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 *  Test class for Level.
 */
public class AvatarLevelTest {

    /**
     * Checks if a newly constructed avatar has the correct Level and Xp.
     */
    @Test
    public void checkNewLevelTest() {
        AvatarLevel avatarLevelTest = new AvatarLevel();

        // Level and Xp should be 1 and 0 respectively for it to be true.
        assertEquals(1, avatarLevelTest.getLevel());
        assertEquals(0, avatarLevelTest.getXp());
    }

    /**
     * Checks if the gain xp function is working properly as intended.
     */
    @Test
    public void checkXpGainTest() {
        AvatarLevel avatarLevelTest = new AvatarLevel();

        // Should increment the xp by 1.
        avatarLevelTest = avatarLevelTest.increaseXp();
        assertEquals(1, avatarLevelTest.getXp());
    }

    /**
     * Checks if the boolean function to determine if the Level can level up
     * is working as intended.
     */
    @Test
    public void checkCanLevelTest() {
        AvatarLevel avatarLevelTest = new AvatarLevel();

        // New Level instance should not be able to level.
        assertFalse(avatarLevelTest.canLevel());

        // Level reaches threshold to level up should return true.
        AvatarLevel avatarLevelUpTest = new AvatarLevel(1, 5);
        assertTrue(avatarLevelUpTest.canLevel());
    }

    /**
     * Tests different instances of Level to determine if they are
     * the same as one another.
     */
    @Test
    public void equals() {
        AvatarLevel avatarLevel1 = new AvatarLevel();
        AvatarLevel avatarLevel2 = new AvatarLevel();

        // Same object should return true.
        assertEquals(avatarLevel1, avatarLevel1);

        // Same values should return true.
        assertEquals(avatarLevel1, avatarLevel2);

        // Different values of different levels should return false.
        avatarLevel2 = avatarLevel2.increaseXp();
        assertNotEquals(avatarLevel1, avatarLevel2);
    }

    /**
     * Checks if the toTxt format is working as intended.
     */
    @Test
    public void checkPrintingToTxt() {
        AvatarLevel avatarLevel1 = new AvatarLevel();
        AvatarLevel avatarLevel2 = new AvatarLevel(3, 14);
        AvatarLevel avatarLevel3 = new AvatarLevel(4, 17);

        // The string should represent the level's current Level and Xp respectively, separated by a space.
        assertEquals("1 0", avatarLevel1.toTxt());
        assertEquals("3 14", avatarLevel2.toTxt());
        assertEquals("4 17", avatarLevel3.toTxt());

        // Different levels with different values should not return the same toTxt format.
        assertNotEquals(avatarLevel1.toTxt(), avatarLevel2.toTxt());
    }

    @Test
    public void immutabilityCheck() {
        AvatarLevel newAvatarLevel = new AvatarLevel();

        // Should return the same level instance as before.
        newAvatarLevel.increaseXp().increaseXp().upLevel();
        assertEquals(newAvatarLevel, new AvatarLevel());

        // Supports chaining when creating a new instance.
        AvatarLevel upgradedAvatarLevel = new AvatarLevel(1, 3);
        assertEquals(upgradedAvatarLevel, new AvatarLevel().increaseXp().increaseXp().increaseXp());

    }



}
