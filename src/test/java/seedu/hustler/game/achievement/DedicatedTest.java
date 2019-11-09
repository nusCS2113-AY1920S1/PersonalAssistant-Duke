package seedu.hustler.game.achievement;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * Test for "Dedicated to the art".
 */
public class DedicatedTest {

    /**
     * Checks if Dedicated to the art can be unlock.
     */
    @Test
    public void checkUnlockDedicated() {
        ConsecutiveLogin consecutiveLogin = new ConsecutiveLogin("Bronze");
        consecutiveLogin.setLock(false);
        assertEquals(false, consecutiveLogin.checkLock());
    }

    /**
     * Checks if the condition for unlocking each achievement level is correct.
     */
    @Test
    public void checDedicatedInformation() {
        ConsecutiveLogin bronze = new ConsecutiveLogin("Bronze");
        ConsecutiveLogin silver = new ConsecutiveLogin("Silver");
        ConsecutiveLogin gold = new ConsecutiveLogin("Gold");

        //checks if the condition for getting bronze achievement level is correct
        assertEquals("(User logs in 5 days consecutively)", bronze.getInformation());
        //checks if the condition for getting silver achievement level is correct
        assertEquals("(User logs in 10 days consecutively)", silver.getInformation());
        //checks if the condition for getting gold achievement level is correct
        assertEquals("(User logs in 15 days consecutively)", gold.getInformation());

        //checks if the condition for getting bronze achievement level is different from silver
        assertNotEquals(bronze.getInformation(),silver.getInformation());
    }

    /**
     * Checks if the toTxt format is working as intended.
     */
    @Test
    public void checkPrintingToTxt() {

        ConsecutiveLogin lockedBronze = new ConsecutiveLogin("Bronze");
        ConsecutiveLogin unlockedBronze = new ConsecutiveLogin("Bronze");

        unlockedBronze.setLock(false);
        unlockedBronze.setPoints(5);

        //String should represents the current status of Busybee.
        assertEquals("true|0|Bronze|Dedicated to the art|(User logs in 5 days consecutively)", lockedBronze.toTxt());
        assertEquals("false|5|Bronze|Dedicated to the art|(User logs in 5 days consecutively)", unlockedBronze.toTxt());

    }

    /**
     * Checks if the toString format is working as intended.
     */
    @Test
    public void checkPrintingToString() {

        AddTask lockedBronze = new AddTask("Bronze");

        //String should represents the current status of Busybee.
        assertEquals("Gained: 0 Busybee Bronze (User adds 5 tasks) Progress: [0%]", lockedBronze.toString());
    }
}
