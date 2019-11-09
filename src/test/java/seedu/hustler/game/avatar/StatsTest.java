package seedu.hustler.game.avatar;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 *  Test class for Stats.
 */
public class StatsTest {

    /**
     * Tests if the given stats of a newly constructed stats are correct.
     */
    @Test
    public void newStatsCheck() {
        Stats stats = new Stats();

        // If stats are the baseline stats, return true.
        assertEquals(stats.getDamage(), 1);
        assertEquals(stats.getDefence(), 1);
        assertEquals(stats.getStamina(), 3);
        assertEquals(stats.getSpeed(), 1);
    }

    /**
     * Tests if the given constructed stats are correct.
     */
    @Test
    public void givenStatsCheck() {
        Stats stats1 = new Stats(10, 10, 10, 10);
        Stats stats2 = new Stats(20, 10, 5, 5);

        // If stats are as given, return true.
        assertEquals(stats1.getDamage(), 10);
        assertEquals(stats1.getDefence(), 10);
        assertEquals(stats1.getStamina(), 10);
        assertEquals(stats1.getSpeed(), 10);

        // If stats are not of baseline stats, return false.
        assertNotEquals(stats2.getDamage(), new Stats().getDamage());
        assertNotEquals(stats2.getDefence(), new Stats().getDefence());
        assertNotEquals(stats2.getStamina(), new Stats().getStamina());
        assertNotEquals(stats2.getSpeed(), new Stats().getSpeed());
    }

    /**
     * Tests if  the given stats are correct in the txt format.
     */
    @Test
    public void toTxtCheck() {
        Stats baseStats = new Stats();
        Stats constructedStats = new Stats(10, 10, 10, 15);

        // If txt is as given, return true.
        assertEquals(baseStats.toTxt(), "1 1 3 1");
        assertEquals(constructedStats.toTxt(), "10 10 10 15");
    }

    /**
     * Checks if incrementing the stat line is correct.
     */
    @Test
    public void checkUpStatsValue() {
        Stats baseStats = new Stats();

        // Checks if stats increment of damage, defence, stamina and speed are 2, 1, 2, 1 respectively.
        Stats upgradedStats1 = new Stats().upStats(2);
        assertEquals(upgradedStats1.getDamage(), 1 + 2);
        assertEquals(upgradedStats1.getDefence(), 1 + 1);
        assertEquals(upgradedStats1.getStamina(), 3 + 2);
        assertEquals(upgradedStats1.getSpeed(), 1 + 1);

        // Checks if stats increment of damage, defence, stamina and speed are 1, 2, 2, 1 respectively.
        Stats upgradedStats2 = new Stats().upStats(3);
        assertEquals(upgradedStats2.getDamage(), 1 + 1);
        assertEquals(upgradedStats2.getDefence(), 1 + 2);
        assertEquals(upgradedStats2.getStamina(), 3 + 2);
        assertEquals(upgradedStats2.getSpeed(), 1 + 1);

        // Checks if stats increment of damage, defence, stamina and speed are 2, 2, 2, 1 respectively.
        Stats upgradedStats3 = new Stats().upStats(6);
        assertEquals(upgradedStats3.getDamage(), 1 + 2);
        assertEquals(upgradedStats3.getDefence(), 1 + 2);
        assertEquals(upgradedStats3.getStamina(), 3 + 2);
        assertEquals(upgradedStats3.getSpeed(), 1 + 1);
    }

    /**
     * Checks if stats are equal.
     */
    @Test
    public void equals() {
        Stats baseStats1 = new Stats();
        Stats baseStats2 = new Stats();

        // If same object, returns true.
        assertTrue(baseStats1.equals(baseStats1));

        // If same stat line, returns true.
        assertTrue(baseStats1.equals(baseStats2));

        // If different stat line, returns false.
        baseStats2 = baseStats2.upStats(2);
        assertFalse(baseStats1.equals(baseStats2));
    }
}
