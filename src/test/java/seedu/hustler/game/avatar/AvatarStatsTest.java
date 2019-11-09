package seedu.hustler.game.avatar;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 *  Test class for Stats.
 */
public class AvatarStatsTest {

    /**
     * Tests if the given stats of a newly constructed stats are correct.
     */
    @Test
    public void newStatsCheck() {
        AvatarStats avatarStats = new AvatarStats();

        // If stats are the baseline stats, return true.
        assertEquals(avatarStats.getDamage(), 1);
        assertEquals(avatarStats.getDefence(), 1);
        assertEquals(avatarStats.getStamina(), 3);
        assertEquals(avatarStats.getSpeed(), 1);
    }

    /**
     * Tests if the given constructed stats are correct.
     */
    @Test
    public void givenStatsCheck() {
        AvatarStats avatarStats1 = new AvatarStats(10, 10, 10, 10);
        AvatarStats avatarStats2 = new AvatarStats(20, 10, 5, 5);

        // If stats are as given, return true.
        assertEquals(avatarStats1.getDamage(), 10);
        assertEquals(avatarStats1.getDefence(), 10);
        assertEquals(avatarStats1.getStamina(), 10);
        assertEquals(avatarStats1.getSpeed(), 10);

        // If stats are not of baseline stats, return false.
        assertNotEquals(avatarStats2.getDamage(), new AvatarStats().getDamage());
        assertNotEquals(avatarStats2.getDefence(), new AvatarStats().getDefence());
        assertNotEquals(avatarStats2.getStamina(), new AvatarStats().getStamina());
        assertNotEquals(avatarStats2.getSpeed(), new AvatarStats().getSpeed());
    }

    /**
     * Tests if  the given stats are correct in the txt format.
     */
    @Test
    public void toTxtCheck() {
        AvatarStats baseAvatarStats = new AvatarStats();
        AvatarStats constructedAvatarStats = new AvatarStats(10, 10, 10, 15);

        // If txt is as given, return true.
        assertEquals(baseAvatarStats.toTxt(), "1 1 3 1");
        assertEquals(constructedAvatarStats.toTxt(), "10 10 10 15");
    }

    /**
     * Checks if incrementing the stat line is correct.
     */
    @Test
    public void checkUpStatsValue() {
        AvatarStats baseAvatarStats = new AvatarStats();

        // Checks if stats increment of damage, defence, stamina and speed are 2, 1, 2, 1 respectively.
        AvatarStats upgradedAvatarStats1 = new AvatarStats().upStats(2);
        assertEquals(1 + 2, upgradedAvatarStats1.getDamage());
        assertEquals(1 + 1, upgradedAvatarStats1.getDefence());
        assertEquals(3 + 2, upgradedAvatarStats1.getStamina());
        assertEquals(1 + 1, upgradedAvatarStats1.getSpeed());

        // Checks if stats increment of damage, defence, stamina and speed are 1, 2, 2, 1 respectively.
        AvatarStats upgradedAvatarStats2 = new AvatarStats().upStats(3);
        assertEquals(1 + 1, upgradedAvatarStats2.getDamage());
        assertEquals(1 + 2, upgradedAvatarStats2.getDefence());
        assertEquals(3 + 2, upgradedAvatarStats2.getStamina());
        assertEquals(1 + 1, upgradedAvatarStats2.getSpeed());

        // Checks if stats increment of damage, defence, stamina and speed are 2, 2, 2, 1 respectively.
        AvatarStats upgradedAvatarStats3 = new AvatarStats().upStats(6);
        assertEquals(1 + 2, upgradedAvatarStats3.getDamage());
        assertEquals(1 + 2, upgradedAvatarStats3.getDefence());
        assertEquals(3 + 2, upgradedAvatarStats3.getStamina());
        assertEquals(1 + 1, upgradedAvatarStats3.getSpeed());
    }

    @Test
    public void immutabilityCheck() {
        AvatarStats baseAvatarStats = new AvatarStats();

        // Should still return the base stat values.
        baseAvatarStats.upStats(1000);
        assertEquals(baseAvatarStats, new AvatarStats());

        // Should still return the base Stat values.
        baseAvatarStats.upStats(1).upStats(1).upStats(1);
        assertEquals(baseAvatarStats, new AvatarStats());

        // Supports chaining when creating a new instance.
        AvatarStats upgradedAvatarStats = new AvatarStats(3, 2, 5, 2);
        assertEquals(new AvatarStats().upStats(2), upgradedAvatarStats);
    }

    /**
     * Checks if stats are equal.
     */
    @Test
    public void equals() {
        AvatarStats baseAvatarStats1 = new AvatarStats();
        AvatarStats baseAvatarStats2 = new AvatarStats();

        // If same object, returns true.
        assertTrue(baseAvatarStats1.equals(baseAvatarStats1));

        // If same stat line, returns true.
        assertTrue(baseAvatarStats1.equals(baseAvatarStats2));

        // If different stat line, returns false.
        baseAvatarStats2 = baseAvatarStats2.upStats(2);
        assertFalse(baseAvatarStats1.equals(baseAvatarStats2));
    }
}
