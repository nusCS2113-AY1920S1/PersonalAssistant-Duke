package seedu.hustler.game.avatar;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 *  Test class for AvatarLevel. Contains the unit testing for AvatarLevel class.
 */
public class AvatarLevelTest {

    /**
     * The base AvatarLevel to test with.
     */
    private static AvatarLevel test;

    @BeforeAll
    static void start() {
        // Base AvatarLevel has level and xp to be 1 and 0 respectively.
        test = new AvatarLevel();
    }

    /**
     * Tests if a newly constructed avatar has the correct Level and Xp.
     * Also tests the getter for level and xp.
     */
    @Test
    public void newLevelCheck() {
        // Level and Xp should be 1 and 0 respectively for it to be true.
        assertEquals(1, test.getLevel());
        assertEquals(0, test.getXp());
    }

    /**
     * Tests if the gain xp function is working properly as intended.
     */
    @Test
    public void xpGainCheck() {
        // Should increment the xp by 1.
        assertEquals(1, test.increaseXp().getXp());
    }

    /**
     * Tests if the boolean function to determine if the Level can level up
     * is working as intended.
     */
    @Test
    public void canLevelCheck() {
        // New Level instance should not be able to level.
        assertFalse(test.canLevel());

        // Level reaches threshold to level up should return true.
        AvatarLevel avatarLevelUpTest = new AvatarLevel(1, 5);
        assertTrue(avatarLevelUpTest.canLevel());
    }

    /**
     * Tests if the level up function is working as intended.
     */
    @Test
    public void upLevelCheck() {
        // New level should return 1 for level, and leveling up should increment it by 1.
        assertEquals(1, test.getLevel());
        assertEquals(2, test.upLevel().getLevel());
    }

    /**
     * Tests different instances of Level to determine if they are
     * the same as one another.
     */
    @Test
    public void equals() {
        AvatarLevel newAvatarLevel = new AvatarLevel();

        // Same object should return true.
        assertEquals(test, test);

        // Same values should return true.
        assertEquals(newAvatarLevel, test);

        // Different values of different levels should return false.
        assertNotEquals(newAvatarLevel, test.increaseXp());
    }

    /**
     * Tests if the toTxt format is working as intended.
     */
    @Test
    public void toTxtCheck() {
        AvatarLevel avatarLevel1 = new AvatarLevel(3, 14);
        AvatarLevel avatarLevel2 = new AvatarLevel(4, 17);

        // The string should represent the level's current Level and Xp respectively, separated by a space.
        assertEquals("1 0", test.toTxt());
        assertEquals("1 1", test.increaseXp().toTxt());
        assertEquals("3 14", avatarLevel1.toTxt());
        assertEquals("4 17", avatarLevel2.toTxt());

        // Different levels with different values should not return the same toTxt format.
        assertNotEquals(avatarLevel1.toTxt(), avatarLevel2.toTxt());
    }

    /**
     * Tests if the toString return is correct.
     */
    @Test
    public void toStringCheck() {
        String str;

        // The string for a new AvatarLevel should return this.
        str = "Level: 1 | XP: 0";
        assertEquals(str, test.toString());

        // The string when AvatarLevel increments Xp should return this.
        str = "Level: 1 | XP: 1";
        assertEquals(str, test.increaseXp().toString());

        // The string when AvatarLevel increments Level should return this.
        str = "Level: 2 | XP: 0";
        assertEquals(str, test.upLevel().toString());
    }

    /**
     * Tests if the AvatarLevel class actually implements immutability.
     */
    @Test
    public void immutabilityCheck() {
        // Should return the same level instance as before.
        test.increaseXp().increaseXp().upLevel();
        assertEquals(test, new AvatarLevel());

        // Supports chaining when creating a new instance.
        AvatarLevel upgradedAvatarLevel = new AvatarLevel(1, 3);
        assertEquals(upgradedAvatarLevel, test.increaseXp().increaseXp().increaseXp());
    }
}
