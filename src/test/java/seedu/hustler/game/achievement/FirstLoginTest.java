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
        FirstLogin firstLogin = new FirstLogin();
        firstLogin.setLock(false);
        assertEquals(false, firstLogin.checkLock());
    }

    /**
     * Checks if the condition for unlocking each achievement level is correct.
     */
    @Test
    public void checkFirstLoginInformation() {
        FirstLogin gold = new FirstLogin();

        //checks if the condition for getting bronze achievement level is correct
        assertEquals("(User use Hustler for the first time) Progress: [100%]", gold.getInformation());
    }

    /**
     * Checks if the toTxt format is working as intended.
     */
    @Test
    public void checkPrintingToTxt() {

        FirstLogin unlockedGold = new FirstLogin();

        //String should represents the current status of Busybee.
        assertEquals("false|15|Gold|Fresh off the boat|(User use Hustler for the first time) Progress: [100%]", unlockedGold.toTxt());

    }

    /**
     * Checks if the toString format is working as intended.
     */
    @Test
    public void checkPrintingToString() {

        FirstLogin lockedGold = new FirstLogin();

        //String should represents the current status of Busybee.
        assertEquals("Gained: 15 Fresh off the boat Gold(User use Hustler for the first time) Progress: [100%]", lockedGold.toString());
    }
}
