package RecurringTest;

import Commands.Command;
import Commands.RecurringCommand;
import Commons.LookupTable;
import Commons.UserInteraction;
import StubClasses.StorageStub;
import Tasks.Assignment;
import Tasks.Event;
import Tasks.TaskList;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This class tests RecurCommand.
 */
public class RecurringCommandTest {
    private static TaskList events = new TaskList();
    private static TaskList deadlines = new TaskList();
    private UserInteraction ui = new UserInteraction();
    private StorageStub storageStub = new StorageStub();
    private static String description;
    private static String testDescription;
    private static String startWeekDate;
    private static Date startDate;
    private static String startDateString;
    private static String followingWeekDate;
    private static Date followingDate;
    private static String followingDateString;
    private static String endWeekDate;
    private static Date endDate;
    private static String endDateString;
    private static String dateBeforeStartWeekDate;
    private static Date dateBeforeStartDate;
    private static String dateBeforeStartDateString;
    private static String dateAfterEndWeekDate;
    private static Date dateAfterEndDate;
    private static String dateAfterEndDateString;
    private static Date startTime;
    private static String startTimeString;
    private static Date endTime;
    private static String endTimeString;
    private static String testWeekDate;
    private static Date testDate;
    private static String testDateString;
    private static Date testStartTime;
    private static String testStartTimeString;
    private static Date testEndTime;
    private static String testEndTimeString;
    private static Assignment stubTask;
    private static SimpleDateFormat eventDateInputFormat = new SimpleDateFormat("dd/MM/yyyy");
    private static SimpleDateFormat eventTimeInputFormat = new SimpleDateFormat("HHmm");
    private static SimpleDateFormat dateOutputFormat = new SimpleDateFormat("E dd/MM/yyyy");
    private static SimpleDateFormat timeOutputFormat = new SimpleDateFormat("hh:mm a");
    private static LookupTable lookupTable = LookupTable.getInstance();

    @BeforeAll
    public static void setAllVariables() throws ParseException {
        description = "CS2101 tutorial";
        testDescription = "CS2107 tutorial";
        startWeekDate = lookupTable.getValue("week 9 mon");
        startDate = eventDateInputFormat.parse(startWeekDate);
        startDateString = dateOutputFormat.format(startDate);
        followingWeekDate = lookupTable.getValue("week 10 mon");
        followingDate = eventDateInputFormat.parse(followingWeekDate);
        followingDateString = dateOutputFormat.format(followingDate);
        endWeekDate = lookupTable.getValue("week 12 mon");
        endDate = eventDateInputFormat.parse(endWeekDate);
        endDateString = dateOutputFormat.format(endDate);
        startTime = eventTimeInputFormat.parse("1000");
        startTimeString = timeOutputFormat.format(startTime);
        endTime = eventTimeInputFormat.parse("1200");
        endTimeString = timeOutputFormat.format(endTime);
        dateBeforeStartWeekDate = lookupTable.getValue("week 8 mon");
        dateBeforeStartDate = eventDateInputFormat.parse(dateBeforeStartWeekDate);
        dateBeforeStartDateString = dateOutputFormat.format(dateBeforeStartDate);
        dateAfterEndWeekDate = lookupTable.getValue("week 13 mon");
        dateAfterEndDate = eventDateInputFormat.parse(dateAfterEndWeekDate);
        dateAfterEndDateString = dateOutputFormat.format(dateAfterEndDate);
        testWeekDate = lookupTable.getValue("week 7 mon");
        testDate = eventDateInputFormat.parse(testWeekDate);
        testDateString = dateOutputFormat.format(testDate);
        testStartTime = eventTimeInputFormat.parse("0900");
        testStartTimeString = timeOutputFormat.format(testStartTime);
        testEndTime = eventTimeInputFormat.parse("0930");
        testEndTimeString = timeOutputFormat.format(testEndTime);
        stubTask = new Event(description, startDateString, startTimeString, endTimeString);
        events.addTask(stubTask);
        stubTask = new Event(description, followingDateString, startTimeString, endTimeString);
        events.addTask(stubTask);
        stubTask = new Event(description, dateBeforeStartDateString, startTimeString, endTimeString);
        events.addTask(stubTask);
        stubTask = new Event(description, testDateString, startTimeString, endTimeString);
        events.addTask(stubTask);
        stubTask = new Event(testDescription, dateAfterEndDateString, startTimeString, endTimeString);
        events.addTask(stubTask);
    }

    @Test
    public void addRecurringTask_clashesWithExistedEvent() {
        Command command = new RecurringCommand(testDescription, dateAfterEndDateString, endDateString, startTimeString,
                endTimeString, false, true);
        String expected = "Sorry, you have conflicting events \n" + "1. [E][✘] CS2107 tutorial by "
                + "Mon 11/11/2019 10:00 AM to 12:00 PM\n";
        String actual = "";
        try {
            actual = command.execute(events, deadlines, ui, storageStub);
        } catch (Exception e) {
            actual = e.getMessage();
        }
        assertEquals(expected, actual);
    }

    @Test
    public void removeRecurringTask_withInvalidModCode_throwDukeException() {
        Command command = new RecurringCommand("CS2030 tutorial", startDateString, endDateString,
                startTimeString, endTimeString, false, false);
        String expected = "Sorry, you have no such mod in the system";
        String actual = "";
        try {
            actual = command.execute(events, deadlines, ui, storageStub);
        } catch (Exception e) {
            actual = e.getMessage();
        }
        assertEquals(expected, actual);
    }

    @Test
    public void removeRecurringTask_withInvalidDate_throwDukeException() {
        Command command = new RecurringCommand(description, endDateString, dateAfterEndDateString,
                startTimeString, endTimeString, false, false);
        String expected = "Sorry, you have no such date of the mod in the system";
        String actual = "";
        try {
            actual = command.execute(events, deadlines, ui, storageStub);
        } catch (Exception e) {
            actual = e.getMessage();
        }
        assertEquals(expected, actual);
    }

    @Test
    public void removeRecurringTask_withInvalidTiming_throwDukeException() {
        Command command = new RecurringCommand(description, startDateString, endDateString, endTimeString,
                startTimeString, false, false);
        String expected = "Sorry, you have no timing of the task in the system";
        String actual = "";
        try {
            actual = command.execute(events, deadlines, ui, storageStub);
        } catch (Exception e) {
            actual = e.getMessage();
        }
        assertEquals(expected, actual);
    }

    @Test
    public void addWeeklyRecurringCommand_withValidFormat() {
        Command command = new RecurringCommand(testDescription, dateBeforeStartDateString, startDateString,
                testStartTimeString, testEndTimeString, false, true);
        String expected = "Weekly recurring task: " + testDescription + " has been added between "
                + dateBeforeStartDateString + " and " + startDateString + "\n";
        String actual = "";
        try {
            actual = command.execute(events, deadlines, ui, storageStub);
        } catch (Exception e) {
            actual = e.getMessage();
        }
        assertEquals(expected, actual);
    }

    @Test
    public void removeWeeklyRecurringCommand_withValidFormat() {
        Command command = new RecurringCommand(description, startDateString, followingDateString,
                startTimeString, endTimeString, false, false);
        String expected = "Weekly recurring task: " + description + " has been removed between " + startDateString
                + " and " + followingDateString + "\n";
        String actual = "";
        try {
            actual = command.execute(events, deadlines, ui, storageStub);
        } catch (Exception e) {
            actual = e.getMessage();
        }

        assertEquals(expected, actual);
    }

    @Test
    public void addBiweeklyRecurringCommand_withValidFormat() {
        Command command = new RecurringCommand(testDescription, followingDateString, endDateString,
                testStartTimeString, testEndTimeString, true, true);
        String expected = "Biweekly recurring task: " + testDescription + " has been added between "
                + followingDateString + " and " + endDateString + "\n";
        String actual = "";
        try {
            actual = command.execute(events, deadlines, ui, storageStub);
        } catch (Exception e) {
            actual = e.getMessage();
        }
        assertEquals(expected, actual);
    }

    @Test
    public void removeBiweeklyRecurringCommand_withValidFormat() {
        Command command = new RecurringCommand(description, testDateString, dateBeforeStartDateString,
                startTimeString, endTimeString, true, false);
        String expected = "Biweekly recurring task: " + description + " has been removed between " + testDateString
                + " and " + dateBeforeStartDateString + "\n";
        String actual = "";
        try {
            actual = command.execute(events, deadlines, ui, storageStub);
        } catch (Exception e) {
            actual = e.getMessage();
        }
        assertEquals(expected, actual);
    }
}
