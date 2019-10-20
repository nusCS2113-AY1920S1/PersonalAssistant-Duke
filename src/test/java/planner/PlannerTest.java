package planner;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlannerTest extends InputTest {

    private static final String LINE = "_______________________________\n";

    /**
     * Testing the Duke class with bye as the only input
     * and checking the expected output.
     * outContent must have it's carriage return removed due
     * to windows/linus newline differences.
     */

    public void testPlan() {
        final String test = "bye";
        provideInput(test);
        final String[] hold = { test, "what" };
        Planner.main(hold);
        String expected =
                LINE
                + "Hello! I'm Duke\n"
                + "What can I do for you?\n"
                + LINE
                + "\n"
                + LINE
                + "\n"
                + "Bye. Hope to see you again soon!\n"
                + LINE
                + "\n";
        assertEquals(expected, outContent.toString().replace("\r", ""));
    }
}
