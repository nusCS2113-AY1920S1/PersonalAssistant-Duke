package RemindTest;

import Commands.Command;
import Commands.RemindCommand;
import Commons.DukeConstants;
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
    private static Assignment dummyTask;
    private static String reminderSetDateString;
    private static String reminderRemoveDateString;
    private static String tenMinAfterCurrentDateString;
    private static Date oneMinBeforeCurrentDate;
    private static Date oneMinAfterCurrentDate;
    private static Date tenMinAfterCurrentDate;
    private static Date threeDaysAfterCurrentDate;
    private static SimpleDateFormat dateOutputFormat = new SimpleDateFormat("E dd/MM/yyyy");
    private static SimpleDateFormat timeOutputFormat = new SimpleDateFormat("hh:mm a");
    private static SimpleDateFormat deadlineDateFormat = new SimpleDateFormat("E dd/MM/yyyy hh:mm a");
    private StorageStub storageStub = new StorageStub();
    private TaskList events = new TaskList();
    private UserInteraction ui = new UserInteraction();

    /**
     * This method initializes the variables required.
     */
    @BeforeAll
    public static void setAllVariables() {
        Date dayAfter = new Date(System.currentTimeMillis() + 86400000);
        Date dayBefore = new Date(System.currentTimeMillis() - 86400000);
        Date dayAfterTomorrow = new Date(System.currentTimeMillis() + 172800000);
        String dateDayAfterTomorrow = dateOutputFormat.format(dayAfterTomorrow);
        String timeDayAfterTomorrow = timeOutputFormat.format(dayAfterTomorrow);
        threeDaysAfterCurrentDate = new Date(System.currentTimeMillis() + 259200000);
        String dateAfter = dateOutputFormat.format(dayAfter);
        String timeAfter = timeOutputFormat.format(dayAfter);
        String dateBefore = dateOutputFormat.format(dayBefore);
        String timeBefore = timeOutputFormat.format(dayBefore);
        taskAfterCurrentDate = new Deadline("CS2100 exam", dateAfter, timeAfter);
        taskBeforeCurrentDate = new Deadline("CS2100 exam", dateBefore, timeBefore);
        taskWithInvalidModCode = new Deadline("CS2107 exam", dateAfter, timeAfter);
        taskWithInvalidDescription = new Deadline("CS2100 assignment", dateAfter, timeAfter);
        String dateThreeDaysAfter = dateOutputFormat.format(threeDaysAfterCurrentDate);
        String timeThreeDaysAfter = timeOutputFormat.format(threeDaysAfterCurrentDate);
        taskWithInvalidDate = new Deadline("CS2100 exam", dateDayAfterTomorrow, timeDayAfterTomorrow);
        taskWithReminder = new Deadline("CS2100 exam", dateThreeDaysAfter, timeThreeDaysAfter);
        oneMinAfterCurrentDate = new Date(System.currentTimeMillis() + 60000);
        oneMinBeforeCurrentDate = new Date(System.currentTimeMillis() - 60000);
        tenMinAfterCurrentDate = new Date(System.currentTimeMillis() + 600000);
        tenMinAfterCurrentDateString = deadlineDateFormat.format(tenMinAfterCurrentDate);
        reminderSetDateString = deadlineDateFormat.format(oneMinAfterCurrentDate);
        reminderRemoveDateString = deadlineDateFormat.format(tenMinAfterCurrentDate);


        dummyTask = new Deadline(DukeConstants.NO_FIELD, DukeConstants.NO_FIELD, DukeConstants.NO_FIELD);
        deadlines.addTask(taskAfterCurrentDate);
        deadlines.addTask(taskWithReminder);
        ReminderStub.setReminderTask(taskWithReminder);
        ReminderStub.setReminderTime(tenMinAfterCurrentDate);
    }

    @Test
    public void execute_setRemindTaskDateBeforeCurrentDate_throwsDukeInvalidDateTimeException() {
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
    public void execute_setRemindDateBeforeCurrentDate_throwsDukeInvalidDateTimeException() {
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
    public void execute_setRemindWithInvalidModCode_throwsDukeException() {
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
    public void execute_setRemindWithInvalidTaskDate_throwsDukeException() {
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
    public void execute_setReminderWithInvalidTaskDescription_throwsDukeException() {
        Command command = new RemindCommand(taskWithInvalidDescription, oneMinAfterCurrentDate,true);
        String expected = "Sorry, the description of your task mismatches";
        String actual = "";
        try {
            actual = command.execute(events, deadlines, ui, storageStub);
        } catch (Exception e) {
            actual = e.getMessage();
        }
        assertEquals(expected, actual);
    }

    @Test
    public void execute_setRemindDateThatExists_throwsDukeInvalidDateTimeException() {
        Command command = new RemindCommand(taskWithReminder, tenMinAfterCurrentDate,true);
        String expected = "Sorry, you have a reminder set for " + taskWithReminder.getModCode()
                + " " + taskWithReminder.getDescription() + " by: " + taskWithReminder.getDateTime();
        String actual = "";
        try {
            actual = command.execute(events, deadlines, ui, storageStub);
        } catch (Exception e) {
            actual = e.getMessage();
        }
        assertEquals(expected, actual);
    }

    @Test
    public void execute_removeReminderWithInvalidReminderTime_throwsDukeInvalidDateTimeException() {
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
    public void execute_setReminderAfterDateOfTask_throwsDukeInvalidDateTimeException() {
        Command command = new RemindCommand(taskAfterCurrentDate, threeDaysAfterCurrentDate, true);
        String expected = "Sorry, you cannot set a reminder after the date of the task.";
        String actual = "";
        try {
            actual = command.execute(events, deadlines, ui, storageStub);
        } catch (Exception e) {
            actual = e.getMessage();
        }
        assertEquals(expected, actual);
    }

    @Test
    public void execute_removeReminderWithInvalidDescription_throwsDukeException() {
        Command command = new RemindCommand(taskWithInvalidDescription, tenMinAfterCurrentDate,false);
        String expected = "Sorry, you have no such reminder with inputted description at that time.";
        String actual = "";
        try {
            actual = command.execute(events, deadlines, ui, storageStub);
        } catch (Exception e) {
            actual = e.getMessage();
        }
        assertEquals(expected, actual);
    }

    @Test
    public void execute_checkReminder() {
        Command command = new RemindCommand(dummyTask, tenMinAfterCurrentDate,false);
        String expected = "Here is the list of reminders set:\n\n" + "1. ModCode: CS2100\n" + "Description: exam \n"
                + "Deadline date: " + taskWithReminder.getDateTime() + "\n"
                + "Reminder date: " + tenMinAfterCurrentDateString;
        String actual = "";
        try {
            actual = command.execute(events, deadlines, ui, storageStub);
        } catch (Exception e) {
            actual = e.getMessage();
        }
        assertEquals(expected, actual);
    }

    @Test
    public void execute_setReminderWithValidFormat() {
        Command command = new RemindCommand(taskAfterCurrentDate, oneMinAfterCurrentDate,true);
        String expected = "Reminder has been set for " + taskAfterCurrentDate.getModCode()
                + " " + taskAfterCurrentDate.getDescription() + "at: " + reminderSetDateString;
        String actual = "";
        try {
            actual = command.execute(events, deadlines, ui, storageStub);
        } catch (Exception e) {
            actual = e.getMessage();
        }
        assertEquals(expected, actual);
    }

    @Test
    public void execute_removeReminderWithValidFormat() {
        Command command = new RemindCommand(taskWithReminder, tenMinAfterCurrentDate,false);
        String expected = "Reminder has been removed for " + taskWithReminder.getModCode() + " "
                + taskWithReminder.getDescription() + "on: " + reminderRemoveDateString;
        String actual = "";
        try {
            actual = command.execute(events, deadlines, ui, storageStub);
        } catch (Exception e) {
            actual = e.getMessage();
        }
        assertEquals(expected, actual);
    }
}
