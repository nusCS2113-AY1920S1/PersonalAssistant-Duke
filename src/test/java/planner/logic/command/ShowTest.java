package planner.logic.command;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import planner.InputTest;
import planner.main.CliLauncher;

public class ShowTest extends InputTest {

    /**
     * For testing, the following format of command to be tested, followed by the bye command must be followed.
     * This enforces that the scanner object is closed and prevents test cases from causing infinite loops.
     */
    @DisplayName("Show module test")
    @Test
    public void testShow() {
        final String test = "show module\n";
        final String bye = "bye";
        provideInput("password\n" + test + bye);
        final String[] hold = {"a"};
        CliLauncher.main(hold);
        String actual = outContent.toString().replaceAll("\r", "");
        assertEquals(actual, actual);
    }
}
