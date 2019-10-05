package owlmoney.logic.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import owlmoney.model.task.Task;
import owlmoney.model.task.TaskList;

/**
 * Class to test the functionality of ReminderCommand.
 */
public class ReminderCommandTest {

    private static final DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");

    private ArrayList<String> stringTasks = new ArrayList<>();
    private String input;
    private int num;
    private LocalDateTime now = LocalDateTime.now();

    /**
     * Tests whether the correct exception is thrown when invalid data is provided during intitialization.
     */
    @Test
    void reminderCommand_InvalidDataForInitializeObject_throwsDukeException() {
        input = "a";
        DukeException err = assertThrows(DukeException.class, () -> new ReminderCommand(input));
        assertEquals("The task number should be numeric and cannot be blank", err.getMessage());
    }

    /**
     * Tests whether the correct exception is thrown when empty String is provided during intitialization.
     */
    @Test
    void reminderCommand_EmptyStringForInitializeObject_throwsDukeException() {
        input = "";
        DukeException err = assertThrows(DukeException.class, () -> new ReminderCommand(input));
        assertEquals("The task number should be numeric and cannot be blank", err.getMessage());
    }

    /**
     * Tests whether the correct exception is thrown when white space is provided during intitialization.
     */
    @Test
    void reminderCommand_WhiteSpaceForInitializeObject_throwsDukeException() {
        input = " ";
        DukeException err = assertThrows(DukeException.class, () -> new ReminderCommand(input));
        assertEquals("The task number should be numeric and cannot be blank", err.getMessage());
    }

    /**
     * Tests whether the correct exception is thrown when a large integer value is provided during intitialization.
     */
    @Test
    void reminderCommand_largeIntegerValueForInitializeObject_throwsDukeException() {
        input = "999999999999999999999999";
        DukeException err = assertThrows(DukeException.class, () -> new ReminderCommand(input));
        assertEquals("The number cannot exceed 9 digits", err.getMessage());
    }

    /**
     * Tests whether the correct number of tasks is return when checking for due task in next 10 day.
     */
    @Test
    void checkReminder_checkReminderfor10Days_expectOneTask() {
        stringTasks.add("E | 0 | 9 | meeting 1 | 14 | " + now.plusDays(8).format(inputFormatter).toString());
        TaskList tasks = new TaskList(stringTasks);
        num = 10;
        ArrayList<Task> compare = tasks.checkReminder(num);
        assertEquals(1, compare.size());
    }

    /**
     * Tests whether the correct number of tasks is return when checking for due task in next 1 day.
     */
    @Test
    void checkReminder_checkReminderforOneDays_expectZeroTask() {
        stringTasks.add("E | 0 | 9 | meeting 1 | 14 | " + now.plusDays(8).format(inputFormatter).toString());
        TaskList tasks = new TaskList(stringTasks);
        num = 1;
        ArrayList<Task> compare = tasks.checkReminder(num);
        assertEquals(compare.size(), 0);
    }

    /**
     * Tests whether the correct number of tasks is return when checking for due task in next 0 (current) day.
     */
    @Test
    void checkReminder_checkReminderforZeroDays_expectOneTask() {
        stringTasks.add("E | 0 | 9 | meeting 1 | 14 | " + now.plusHours(1).format(inputFormatter).toString());
        TaskList tasks = new TaskList(stringTasks);
        num = 0;
        ArrayList<Task> compare = tasks.checkReminder(num);
        assertEquals(compare.size(), 1);
    }

    /**
     * Tests whether the correct number of task is being return correctly when searching among multiple tasks.
     */
    @Test
    void checkReminder_checkReminderForMultipleTask_expectThreeTask() {
        stringTasks.add("E | 0 | 9 | meeting 1 | 14 | " + now.plusDays(1).format(inputFormatter).toString());
        stringTasks.add("E | 0 | 9 | meeting 2 | 14 | " + now.plusDays(3).format(inputFormatter).toString());
        stringTasks.add("D | 0 | 9 | meeting 3 | 14 | " + now.plusDays(5).format(inputFormatter).toString());
        stringTasks.add("E | 0 | 9 | meeting 4 | 14 | " + now.plusDays(7).format(inputFormatter).toString());
        stringTasks.add("E | 0 | 9 | meeting 5 | 14 | " + now.plusDays(10).format(inputFormatter).toString());
        TaskList tasks = new TaskList(stringTasks);
        num = 5;
        ArrayList<Task> compare = tasks.checkReminder(num);
        assertEquals(compare.size(), 3);
    }
}
