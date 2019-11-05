package RecurringTest;

import Commands.Command;
import Commands.RecurringCommand;
import Commons.LookupTable;
import Commons.Ui;
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
    private static LookupTable lookupTable = new LookupTable();
    private Ui ui = new Ui();
    private StorageStub storageStub = new StorageStub();
    private static String description;
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
    private static Assignment stubTask;
    private static SimpleDateFormat eventDateInputFormat = new SimpleDateFormat("dd/MM/yyyy");
    private static SimpleDateFormat eventTimeInputFormat = new SimpleDateFormat("HHmm");
    private static SimpleDateFormat dateOutputFormat = new SimpleDateFormat("E dd/MM/yyyy");
    private static SimpleDateFormat timeOutputFormat = new SimpleDateFormat("hh:mm a");

    @BeforeAll
    public static void setAllVariables() throws ParseException {
        description = "CS2101 tutorial";
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
        stubTask = new Event(description, startDateString, startTimeString, endTimeString);
        events.addTask(stubTask);
        stubTask = new Event(description, followingDateString, startTimeString, endTimeString);
        events.addTask(stubTask);
        stubTask = new Event(description, endDateString, startTimeString, endTimeString);
        events.addTask(stubTask);
    }

    @Test
    public void addRecurringTaskClashesWithExistedEventTest() {
        Command command = new RecurringCommand(description, dateBeforeStartDateString, endDateString, startTimeString, endTimeString, false, true);
        String expected = "Sorry, you have similar event at the same time on the same day";
        String actual = "";
        try {
            actual = command.execute(lookupTable, events, deadlines, ui, storageStub);
        } catch (Exception e) {
            actual = e.getMessage();
        }
        assertEquals(expected, actual);
    }

    @Test
    public void removeRecurringTaskWithInvalidModCodeTest() {
        Command command = new RecurringCommand("CS2030 tutorial", startDateString, endDateString, startTimeString, endTimeString, false, false);
        String expected = "Sorry, you have no such recurring mod task to be removed";
        String actual = "";
        try {
            actual = command.execute(lookupTable, events, deadlines, ui, storageStub);
        } catch (Exception e) {
            actual = e.getMessage();
        }
        assertEquals(expected, actual);
    }

    @Test
    public void removeRecurringTaskWithInvalidDateTest() {
        Command command = new RecurringCommand(description, endDateString, dateAfterEndDateString, startTimeString, endTimeString, false, false);
        String expected = "Sorry, you have no such date of the recurring task to be removed";
        String actual = "";
        try {
            actual = command.execute(lookupTable, events, deadlines, ui, storageStub);
        } catch (Exception e) {
            actual = e.getMessage();
        }
        assertEquals(expected, actual);
    }

    @Test
    public void removeRecurringTaskWithInvalidTimingTest() {
        Command command = new RecurringCommand(description, startDateString, endDateString, endTimeString, startTimeString, false, false);
        String expected = "Sorry, you have no timing of the mod task to be removed";
        String actual = "";
        try {
            actual = command.execute(lookupTable, events, deadlines, ui, storageStub);
        } catch (Exception e) {
            actual = e.getMessage();
        }
        assertEquals(expected, actual);
    }

    @Test
    public void addWeeklyRecurringCommandValidFormat() {
        Command command = new RecurringCommand(description, endDateString, dateAfterEndDateString, startTimeString, endTimeString, false, true);
        String expected = "Weekly recurring task: " + description + " has been added between " + endDateString + " and " + dateAfterEndDateString + "\n";
        String actual = "";
        try {
            actual = command.execute(lookupTable, events, deadlines, ui, storageStub);
        } catch (Exception e) {
            actual = e.getMessage();
        }

        assertEquals(expected, actual);
    }

    @Test
    public void removeWeeklyRecurringCommandValidFormat() {
        Command command = new RecurringCommand(description, endDateString, dateAfterEndDateString, startTimeString, endTimeString, false, false);
        String expected = "Weekly recurring task: " + description + " has been removed between " + endDateString + " and " + dateAfterEndDateString + "\n";
        String actual = "";
        try {
            actual = command.execute(lookupTable, events, deadlines, ui, storageStub);
        } catch (Exception e) {
            actual = e.getMessage();
        }

        assertEquals(expected, actual);
    }

    @Test
    public void addBiweeklyRecurringCommandValidFormat() {
        Command command = new RecurringCommand(description, endDateString, dateAfterEndDateString, startTimeString, endTimeString, true, true);
        String expected = "Biweekly recurring task: " + description + " has been added between " + endDateString + " and " + dateAfterEndDateString + "\n";
        String actual = "";
        try {
            actual = command.execute(lookupTable, events, deadlines, ui, storageStub);
        } catch (Exception e) {
            actual = e.getMessage();
        }
        assertEquals(expected, actual);
    }

    @Test
    public void removeBiweeklyRecurringCommandValidFormat() {
        Command command = new RecurringCommand(description, endDateString, dateAfterEndDateString, startTimeString, endTimeString, true, false);
        String expected = "Biweekly recurring task: " + description + " has been removed between " + endDateString + " and " + dateAfterEndDateString + "\n";
        String actual = "";
        try {
            actual = command.execute(lookupTable, events, deadlines, ui, storageStub);
        } catch (Exception e) {
            actual = e.getMessage();
        }
        assertEquals(expected, actual);
    }
}
