//@@author namiwa

package planner.main;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import planner.logic.command.CommandTest;
import planner.logic.exceptions.legacy.ModException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlannerTest extends CommandTest {

    private static final String LINE = "_______________________________";

    public PlannerTest() throws ModException {
    }

    /**
     * Testing the Planner class with bye as the only input
     * and checking the expected output.
     * outContent must have it's carriage return removed due
     * to windows/linus newline differences.
     */
    @DisplayName("Termination Test")
    @Test
    public void testPlan() {
        final String test = "bye";
        execute(test);
        String expected =
                  LINE
                + "\n"
                + "Thanks for using ModPlan!\n"
                + "Your data will be stored in file shortly!\n"
                + LINE
                + "\n";
        assertEquals(expected, getOut());
    }
}
