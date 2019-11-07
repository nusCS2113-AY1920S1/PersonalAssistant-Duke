package RecurringTest;

import Commands.Command;
import Parser.RecurParse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
/**
 * This class tests RecurParse.
 */
public class RecurParseTest {

    private static String startDate;
    private static String endDate;
    private static String startTime;
    private static String endTime;

    @BeforeAll
    public static void setAllVariables() {
        startDate = "week 9 mon";
        endDate = "week 12 mon";
        startTime = "1000";
        endTime = "1200";
    }

    @Test
    public void recurParseTestWIthInvalidModCode() {
        String recurSet = "recur/weekly 2101 tutorial /start " + startDate + " /to " + endDate + " /from " + startTime + " /to " + endTime;
        String expected = "\u2639" + " OOPS!!! The ModCode is invalid";
        String actual = "";
        Command command = null;
        try {
            command = new RecurParse(recurSet).parse();
        } catch (Exception e) {
            actual = e.getMessage();
        }
        assertEquals(expected, actual);
        assertNull(command);
    }

   @Test
    public void recurParseTestWIthInvalidDescription() {
        String recurSet = "recur/weekly CS2101 /start " + startDate + " /to " + endDate + " /from " + startTime + " /to " + endTime;
        String expected = "\u2639" + "  OOPS!!! The description of a event cannot be empty";
        String actual = "";
        Command command = null;
        try {
            command = new RecurParse(recurSet).parse();
        } catch (Exception e) {
            actual = e.getMessage();
        }
        assertEquals(expected, actual);
        assertNull(command);
    }

    @Test
    public void recurParseTestWIthInvalidFormat() {
        String recurSet = "recur/weekly CS2101 tutorial " + startDate + " /to " + endDate + " /from " + startTime + " /to " + endTime;
        String expected = "OOPS!!! Please enter recurring event as follows:\n" +
                "recur/(fill) modCode name_of_event /start dd/MM/yyyy to dd/MM/yyyy /from HHmm /to HHmm\n" +
                "Note: replace (fill) with either: weekly, biweekly, rmweekly, rmbiweekly\n" +
                "For example: recur/weekly CS1231 project meeting /start 1/10/2019 to 15/11/2019 /from 1500 /to 1700";
        String actual = "";
        Command command = null;
        try {
            command = new RecurParse(recurSet).parse();
        } catch (Exception e) {
            actual = e.getMessage();
        }
        assertEquals(expected, actual);
        assertNull(command);
    }

    @Test
    public void recurWeeklyParseWithValidFormat() {
        String recurSet = "recur/weekly CS2101 tutorial /start " + startDate + " /to " + endDate + " /from " + startTime + " /to " + endTime;
        Command command = null;
        String actual = "No error";
        try {
            command = new RecurParse(recurSet).parse();
        } catch (Exception e) {
            actual = e.getMessage();
        }
        assertNotNull(command, actual);
    }

    @Test
    public void recurBiweeklyParseWithValidFormat() {
        String recurSet = "recur/biweekly CS2101 tutorial /start " + startDate + " /to " + endDate + " /from " + startTime + " /to " + endTime;
        Command command = null;
        String actual = "No error";
        try {
            command = new RecurParse(recurSet).parse();
        } catch (Exception e) {
            actual = e.getMessage();
        }
        assertNotNull(command, actual);
    }

    @Test
    public void recurRmweeklyParseWithValidFormat() {
        String recurSet = "recur/rmweekly CS2101 tutorial /start " + startDate + " /to " + endDate + " /from " + startTime + " /to " + endTime;
        Command command = null;
        String actual = "No error";
        try {
            command = new RecurParse(recurSet).parse();
        } catch (Exception e) {
            actual = e.getMessage();
        }
        assertNotNull(command, actual);
    }

    @Test
    public void recurRmbiweeklyParseWithValidFormat() {
        String recurSet = "recur/rmbiweekly CS2101 tutorial /start " + startDate + " /to " + endDate + " /from " + startTime + " /to " + endTime;
        Command command = null;
        String actual = "No error";
        try {
            command = new RecurParse(recurSet).parse();
        } catch (Exception e) {
            actual = e.getMessage();
        }
        assertNotNull(command, actual);
    }
}
