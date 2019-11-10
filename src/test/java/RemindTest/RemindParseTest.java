package RemindTest;

import Commands.Command;
import Parser.RemindParse;
import Tasks.Deadline;
import Tasks.TaskList;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class tests RemindParse.
 */
public class RemindParseTest {
    private static TaskList deadlines = new TaskList();
    private static String by;
    private static String time;
    private static String reminderDate;
    private static String taskDate;
    private static SimpleDateFormat dateOutputFormat = new SimpleDateFormat("E dd/MM/yyyy");
    private static SimpleDateFormat timeOutputFormat = new SimpleDateFormat("hh:mm a");
    private static SimpleDateFormat deadlineInputFormat = new SimpleDateFormat("dd/MM/yyyy HHmm");

    @BeforeAll
    public static void setAllVariables() {
        Date nextDay = new Date(System.currentTimeMillis() + 86400000);
        by = dateOutputFormat.format(nextDay);
        time = timeOutputFormat.format(nextDay);
        Date twoHourFromCurrentDate = new Date(System.currentTimeMillis() + 7200000);
        reminderDate = deadlineInputFormat.format(twoHourFromCurrentDate);
        taskDate = deadlineInputFormat.format(nextDay);
        deadlines.addTask(new Deadline("CS2100 exam", by, time));
    }

    @Test
    public void Parse_SetRemindWithEmptyModCodeAndDescription_ThrowsDukeInvalidFormatException() {
        String remindSet = "remind/set  /by " + taskDate + " /on " + reminderDate;
        String expected = "\u2639" + " OOPS!!! The ModCode + description of a deadline cannot be empty.";
        Command command = null;
        String actual = "";
        try {
            command = new RemindParse(remindSet).parse();
        } catch (Exception e) {
            actual = e.getMessage();
        }
        assertEquals(expected, actual);
        assertNull(command);
    }

    @Test
    public void Parse_SetRemindWithInvalidModCode_ThrowsDukeInvalidFormatException() {
        String remindSet = "remind/set 2100 exam /by " + taskDate + " /on " + reminderDate;
        String expected = "\u2639" + " OOPS!!! The ModCode is invalid";
        String actual = "";
        Command command = null;
        try {
            command = new RemindParse(remindSet).parse();
        } catch (Exception e) {
            actual = e.getMessage();
        }
        assertEquals(expected, actual);
        assertNull(command);
    }

    @Test
    public void Parse_SetRemindWithEmptyDescription_ThrowsDukeInvalidFormatException() {
        String remindSet = "remind/set CS2100 /by " + taskDate + " /on " + reminderDate;
        String expected = "\u2639" + " OOPS!!! The description of a deadline cannot be empty.";
        String actual = "";
        Command command = null;
        try {
            command = new RemindParse(remindSet).parse();
        } catch (Exception e) {
            actual = e.getMessage();
        }
        assertEquals(expected, actual);
        assertNull(command);
    }

    @Test
    public void Parse_SetRemindWithInvalidFormat_ThrowsDukeInvalidFormatException() {
        String remindSet = "remind/set CS2100 " + taskDate + " /on " + reminderDate;
        String expected = "OOPS!!! Please enter remind as follows:\n"
                + "remind/(set/rm) mod_code description /by week n.o day time /on week n.o day time\n"
                + "For example: remind/set cs2100 hand in homework /by week 9 fri 1500 /on week 9 thu 1500";
        String actual = "";
        Command command = null;
        try {
            command = new RemindParse(remindSet).parse();
        } catch (Exception e) {
            actual = e.getMessage();
        }
        assertEquals(expected, actual);
        assertNull(command);
    }

    @Test
    public void Parse_SetRemindWithInvalidDate_ThrowsDukeInvalidFormatException() {
        String remindSet = "remind/set CS2100 " + "week 14 mon" + " /on " + reminderDate;
        String expected = "OOPS!!! Please enter remind as follows:\n"
                + "remind/(set/rm) mod_code description /by week n.o day time /on week n.o day time\n"
                + "For example: remind/set cs2100 hand in homework /by week 9 fri 1500 /on week 9 thu 1500";
        String actual = "";
        Command command = null;
        try {
            command = new RemindParse(remindSet).parse();
        } catch (Exception e) {
            actual = e.getMessage();
        }
        assertEquals(expected, actual);
        assertNull(command);
    }

    @Test
    public void Parse_SetRemindParseWithValidFormat() {
        String remindSet = "remind/set CS2100 exam /by " + taskDate + " /on " + reminderDate;
        Command command = null;
        String actual = "No error";
        try {
            command = new RemindParse(remindSet).parse();
        } catch (Exception e) {
            actual = e.getMessage();
        }
        assertNotNull(command, actual);
    }

    @Test
    public void Parse_RemoveRemindParseWithValidFormat() {
        String remindSet = "remind/rm CS2100 exam /by " + taskDate + " /on " + reminderDate;
        Command command = null;
        String actual = "No error";
        try {
            command = new RemindParse(remindSet).parse();
        } catch (Exception e) {
            actual = e.getMessage();
        }
        assertNotNull(command, actual);
    }

    @Test
    public void Parse_CheckRemindParseWithValidFormat() {
        String remindSet = "remind/check";
        Command command = null;
        String actual = "No error";
        try {
            command = new RemindParse(remindSet).parse();
        } catch (Exception e) {
            actual = e.getMessage();
        }
        assertNotNull(command, actual);
    }
}
