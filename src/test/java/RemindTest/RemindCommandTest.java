package RemindTest;

import Commands.Command;
import Commands.RemindCommand;
import Commons.UserInteraction;
import StubClasses.ReminderStub;
import StubClasses.StorageStub;
import Tasks.Assignment;
import Tasks.Deadline;
import Tasks.TaskList;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * This class tests RemindCommand.
 */
public class RemindCommandTest {
    private static TaskList deadlines = new TaskList();
    private static Assignment taskAfterCurrentDate;
    private static Assignment taskBeforeCurrentDate;
    private static Assignment taskWithInvalidModCode;
    private static Assignment taskWithInvalidDescription;
    private static Assignment taskWithInvalidDate;
    private static Assignment taskWithReminder;
    private static String reminderSetDateString;
    private static String reminderRemoveDateString;
    private static Date oneMinBeforeCurrentDate;
    private static Date oneMinAfterCurrentDate;
    private static Date tenMinAfterCurrentDate;
    private static SimpleDateFormat dateOutputFormat = new SimpleDateFormat("E dd/MM/yyyy");
    private static SimpleDateFormat timeOutputFormat = new SimpleDateFormat("hh:mm a");
    private static SimpleDateFormat deadlineDateFormat = new SimpleDateFormat("E dd/MM/yyyy hh:mm a");
    private StorageStub storageStub = new StorageStub();
    private TaskList events = new TaskList();
    private UserInteraction ui = new UserInteraction();

    @BeforeAll
    public static void setAllVariables() {
        Date dayAfter = new Date(System.currentTimeMillis() + 86400000);
        Date dayBefore = new Date(System.currentTimeMillis() - 86400000);
        Date dayAfterTomorrow = new Date(System.currentTimeMillis() + 172800000);
        Date threeDaysAfterCurrentDate = new Date(System.currentTimeMillis() + 259200000);
        String dateAfter = dateOutputFormat.format(dayAfter);
        String timeAfter = timeOutputFormat.format(dayAfter);
        String dateBefore = dateOutputFormat.format(dayBefore);
        String timeBefore = timeOutputFormat.format(dayBefore);
        String dateDayAfterTomorrow = dateOutputFormat.format(dayAfterTomorrow);
        String timeDayAfterTomorrow = timeOutputFormat.format(dayAfterTomorrow);
        String dateThreeDaysAfter = dateOutputFormat.format(threeDaysAfterCurrentDate);
        String timeThreeDaysAfter = timeOutputFormat.format(threeDaysAfterCurrentDate);
        oneMinAfterCurrentDate = new Date(System.currentTimeMillis() + 60000);
        oneMinBeforeCurrentDate = new Date(System.currentTimeMillis() - 60000);
        tenMinAfterCurrentDate = new Date(System.currentTimeMillis() + 600000);
        reminderSetDateString = deadlineDateFormat.format(oneMinAfterCurrentDate);
        reminderRemoveDateString = deadlineDateFormat.format(tenMinAfterCurrentDate);
        taskAfterCurrentDate = new Deadline("CS2100 exam", dateAfter, timeAfter);
        taskBeforeCurrentDate = new Deadline("CS2100 exam", dateBefore, timeBefore);
        taskWithInvalidModCode = new Deadline("CS2107 exam", dateAfter, timeAfter);
        taskWithInvalidDescription = new Deadline("CS2100 assignment", dateAfter, timeAfter);
        taskWithInvalidDate = new Deadline("CS2100 exam", dateDayAfterTomorrow, timeDayAfterTomorrow);
        taskWithReminder = new Deadline("CS2100 exam", dateThreeDaysAfter, timeThreeDaysAfter);
        deadlines.addTask(taskAfterCurrentDate);
        ReminderStub.setReminderTask(taskWithReminder);
        ReminderStub.setReminderTime(tenMinAfterCurrentDate);
    }

    @Test
    public void taskDateBeforeCurrentDateTest() {
        Command command = new RemindCommand(taskBeforeCurrentDate,oneMinAfterCurrentDate,true);
        String expected = "Sorry, your selected task has already passed!";
        String actual = "";
        try {
            actual = command.execute(events, deadlines, ui, storageStub);
        } catch (Exception e) {
            actual = e.getMessage();
        }
        assertEquals(expected, actual);
    }

    @Test
    public void remindDateBeforeCurrentDateTest() {
        Command command = new RemindCommand(taskAfterCurrentDate, oneMinBeforeCurrentDate,true);
        String expected = "Sorry, you cannot set a time that has already passed!";
        String actual = "";
        try {
            actual = command.execute(events, deadlines, ui, storageStub);
        } catch (Exception e) {
            actual = e.getMessage();
        }
        assertEquals(expected, actual);
    }

    @Test
    public void invalidModCodeTest() {
        Command command = new RemindCommand(taskWithInvalidModCode, oneMinAfterCurrentDate,true);
        String expected = "Sorry, you have no such mod entered in your deadline table!";
        String actual = "";
        try {
            actual = command.execute(events, deadlines, ui, storageStub);
        } catch (Exception e) {
            actual = e.getMessage();
        }
        assertEquals(expected, actual);
    }

    @Test
    public void invalidTaskDateTest() {
        Command command = new RemindCommand(taskWithInvalidDate, oneMinAfterCurrentDate,true);
        String expected = "Sorry, you have no such timing entered in your deadline table!";
        String actual = "";
        try {
            actual = command.execute(events, deadlines, ui, storageStub);
        } catch (Exception e) {
            actual = e.getMessage();
        }
        assertEquals(expected, actual);
    }

    @Test
    public void invalidTaskDescription() {
        Command command = new RemindCommand(taskWithInvalidDescription, oneMinAfterCurrentDate,true);
        String expected = "Sorry, there are no such task description in your deadline table!";
        String actual = "";
        try {
            actual = command.execute(events, deadlines, ui, storageStub);
        } catch (Exception e) {
            actual = e.getMessage();
        }
        assertEquals(expected, actual);
    }

    @Test
    public void remindDateExistsTest() {
        Command command = new RemindCommand(taskWithReminder, tenMinAfterCurrentDate,true);
        String expected = "Sorry, you have a reminder set for " + taskWithReminder.getDescription() + " at: " + taskWithReminder.getDateTime();
        String actual = "";
        try {
            actual = command.execute(events, deadlines, ui, storageStub);
        } catch (Exception e) {
            actual = e.getMessage();
        }
        assertEquals(expected, actual);
    }

    @Test
    public void removeReminderInvalidReminderTime() {
        Command command = new RemindCommand(taskAfterCurrentDate, oneMinAfterCurrentDate,false);
        String expected = "Sorry, you have no such reminder at that inputted time.";
        String actual = "";
        try {
            actual = command.execute(events, deadlines, ui, storageStub);
        } catch (Exception e) {
            actual = e.getMessage();
        }
        assertEquals(expected, actual);
    }

    @Test
    public void removeReminderInvalidDescription() {
        Command command = new RemindCommand(taskWithInvalidDescription, tenMinAfterCurrentDate,false);
        String expected = "Sorry, you have no such reminder with inputted description at that time";
        String actual = "";
        try {
            actual = command.execute(events, deadlines, ui, storageStub);
        } catch (Exception e) {
            actual = e.getMessage();
        }
        assertEquals(expected, actual);
    }

    @Test
    public void setReminderValidFormat() {
        Command command = new RemindCommand(taskAfterCurrentDate, oneMinAfterCurrentDate,true);
        String expected = "Reminder has been set for " + taskAfterCurrentDate.getModCode() + " " + taskAfterCurrentDate.getDescription() + "at: " + reminderSetDateString;
        String actual = "";
        try {
            actual = command.execute(events, deadlines, ui, storageStub);
        } catch (Exception e) {
            actual = e.getMessage();
        }
        assertEquals(expected, actual);
    }

    @Test
    public void removeReminderValidFormat() {
        Command command = new RemindCommand(taskWithReminder, tenMinAfterCurrentDate,false);
        String expected = "Reminder has been removed for " + taskWithReminder.getModCode() + " " + taskWithReminder.getDescription() + "at: " + reminderRemoveDateString;
        String actual = "";
        try {
            actual = command.execute(events, deadlines, ui, storageStub);
        } catch (Exception e) {
            actual = e.getMessage();
        }
        assertEquals(expected, actual);
    }
}
