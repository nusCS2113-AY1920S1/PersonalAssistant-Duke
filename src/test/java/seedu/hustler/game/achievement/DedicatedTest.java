package seedu.hustler.game.achievement;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class DedicatedTest {

    /**
     * Checks if Busybee can be unlock.
     */
    @Test
    public void checkUnlockBusyBee() {
        AddTask addTask = new AddTask("Bronze");
        addTask.setLock(false);
        assertEquals(false, addTask.checkLock());
    }

    /**
     * Checks if the condition for unlocking each achievement level is correct.
     */
    @Test
    public void checkBusyBeeInformation() {
        AddTask bronze = new AddTask("Bronze");
        AddTask silver = new AddTask("Silver");
        AddTask gold = new AddTask("Gold");

        //checks if the condition for getting bronze achievement level is correct
        assertEquals("(User adds 5 tasks)", bronze.getInformation());
        //checks if the condition for getting silver achievement level is correct
        assertEquals("(User adds 10 tasks)", silver.getInformation());
        //checks if the condition for getting gold achievement level is correct
        assertEquals("(User adds 15 tasks)", gold.getInformation());

        //checks if the condition for getting bronze achievement level is different from silver
        assertNotEquals(bronze.getInformation(),silver.getInformation());
    }

    /**
     * Checks if the toTxt format is working as intended.
     */
    @Test
    public void checkPrintingToTxt() {

        AddTask lockedBronze = new AddTask("Bronze");
        AddTask unlockedBronze = new AddTask("Bronze");

        unlockedBronze.setLock(false);
        unlockedBronze.setPoints(5);

        //String should represents the current status of Busybee.
        assertEquals("true|0|Bronze|Busybee|(User adds 5 tasks)", lockedBronze.toTxt());
        assertEquals("false|5|Bronze|Busybee|(User adds 5 tasks)", unlockedBronze.toTxt());

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
