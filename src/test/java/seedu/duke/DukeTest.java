package seedu.duke;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Sample test program for Duke.
 */
public class DukeTest {

    /**
     * Sample test program for getUI().
     */
    @Test
    public void getUiTest() {
        Duke.getUI();
        System.out.println("JUnit testing is running");
        int x = 2;
        assertEquals(2, x);
    }
}
