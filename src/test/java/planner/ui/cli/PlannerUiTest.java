//@@author namiwa

package planner.ui.cli;

import org.junit.jupiter.api.Test;
import planner.main.InputTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PlannerUiTest extends InputTest {

    private PlannerUi plannerUi = new PlannerUi();
    private static final String LINE = "_______________________________";

    @Test
    void testNonNull() {
        assertNotNull(plannerUi);
    }

    @Test
    void printTest() {
        final String test = this.toString();
        plannerUi.print(this);
        plannerUi.println(this);
        assertEquals(test + test + "\n", getReplace());
    }

    @Test
    void testLine() {
        plannerUi.showLine();
        assertEquals(LINE + "\n", getReplace());
    }

    @Test
    void testReminderList() {
        final String expected = LINE
                + "\n"
                + "Would you like to set your reminder to every:\n"
                + "1) for 10 seconds\n"
                + "2) for 30 seconds\n"
                + "3) for 1 minute\n"
                + "4) for 2 minutes\n"
                + "*helpline* : for 1), enter 'reminder one'\n";
        plannerUi.reminderList();
        assertEquals(expected, getReplace());
    }

    @Test
    void testReminderMsg() {
        final String expected = LINE
                + "\n"
                + "Please remember to update your module information!\n"
                + "To do so, you can input the update command in the following format:\n"
                + "update module\n";
        plannerUi.reminderMsg();
        assertEquals(expected, getReplace());
    }

    @Test
    void testHelloMsg() {
        final String expected = LINE
                + "\n"
                +  "Welcome to ModPlan, your one stop solution to module planning!\n"
                + "Begin typing to get started!\n"
                + LINE
                + "\n";
        plannerUi.helloMsg();
        assertEquals(expected, getReplace());
    }

    @Test
    void testByeMsg() {
        final String expected = LINE
                + "\n"
                + "Thanks for using ModPlan!\n"
                + "Your data will be stored in file shortly!\n"
                + LINE
                + "\n";
        plannerUi.goodbyeMsg();
        assertEquals(expected, getReplace());
    }
}
