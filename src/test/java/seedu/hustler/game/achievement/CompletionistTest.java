package seedu.hustler.game.achievement;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * Test for "Completionist".
 */
public class CompletionistTest {

    /**
     * Checks if Completionist can be unlock.
     */
    @Test
    public void checkCompletionistBusyBee() {
        DoneTask doneTask = new DoneTask("Bronze");
        doneTask.setLock(false);
        assertEquals(false, doneTask.checkLock());
    }

    /**
     * Checks if the condition for unlocking each achievement level is correct.
     */
    @Test
    public void checkCompletionistInformation() {
        DoneTask bronze = new DoneTask("Bronze");
        DoneTask silver = new DoneTask("Silver");
        DoneTask gold = new DoneTask("Gold");

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

        DoneTask lockedBronze = new DoneTask("Bronze");
        DoneTask unlockedBronze = new DoneTask("Bronze");

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

        DoneTask lockedBronze = new DoneTask("Bronze");

        //String should represents the current status of Busybee.
        assertEquals("Gained: 0 Completionist Bronze (User completes 5 tasks) Progress: [0%]", lockedBronze.toString());
    }


}
