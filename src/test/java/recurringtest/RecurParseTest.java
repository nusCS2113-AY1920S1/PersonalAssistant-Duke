package recurringtest;

import commands.Command;
import commons.DukeConstants;
import parser.RecurParse;
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

    /**
     * This method initializes the variables required.
     */
    @BeforeAll
    public static void setAllVariables() {
        startDate = "week 9 mon";
        endDate = "week 12 mon";
        startTime = "1000";
        endTime = "1200";
    }

    @Test
    public void recurParseTest_withInvalidModCode_throwDukeInvalidFormatException() {
        String recurSet = "recur/weekly 2101 tutorial /start " + startDate + " /to " + endDate
                + " /from " + startTime + " /to " + endTime;
        String expected = DukeConstants.SAD_FACE + " OOPS!!! The ModCode is invalid";
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
    public void recurParseTest_withInvalidDescription_throwDukeInvalidFormatException() {
        String recurSet = "recur/weekly CS2101 /start " + startDate + " /to " + endDate
                + " /from " + startTime + " /to " + endTime;
        String expected = DukeConstants.SAD_FACE + " OOPS!!! The description of an event cannot be empty.";
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
    public void recurParseTest_withInvalidFormat_throwDukeInvalidFormatException() {
        String recurSet = "recur/weekly CS2101 tutorial " + startDate + " /to " + endDate + " /from " + startTime
                + " /to " + endTime;
        String expected = "OOPS!!! Please enter recurring event as follows:\n"
                + "recur/(fill) modCode name_of_event /start dd/MM/yyyy to dd/MM/yyyy /from HHmm /to HHmm\n"
                + "Note: replace (fill) with either: weekly, biweekly, rmweekly, rmbiweekly\n"
                + "For example: recur/weekly CS1231 project meeting /start 1/10/2019 to 15/11/2019 /from 1500 /to 1700";
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
    public void recurWeeklyParse_withValidFormat() {
        String recurSet = "recur/weekly CS2101 tutorial /start " + startDate + " /to " + endDate
                + " /from " + startTime + " /to " + endTime;
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
    public void recurBiweeklyParse_withValidFormat() {
        String recurSet = "recur/biweekly CS2101 tutorial /start " + startDate + " /to " + endDate
                + " /from " + startTime + " /to " + endTime;
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
    public void recurRmweeklyParse_withValidFormat() {
        String recurSet = "recur/rmweekly CS2101 tutorial /start " + startDate + " /to " + endDate
                + " /from " + startTime + " /to " + endTime;
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
    public void recurRmbiweeklyParse_withValidFormat() {
        String recurSet = "recur/rmbiweekly CS2101 tutorial /start " + startDate + " /to " + endDate
                + " /from " + startTime + " /to " + endTime;
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
