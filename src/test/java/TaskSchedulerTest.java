import chronologer.command.TaskScheduler;
import chronologer.parser.DateTimeExtractor;
import chronologer.task.Task;
import chronologer.task.Event;
import chronologer.task.TaskList;
import org.junit.jupiter.api.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

//@@author fauzt
/**
 * Tests for TaskScheduler logic and ensure it finds the correct periods and return the correct results.
 *
 * @author Fauzan Adipratama
 * @version 1.4
 */
public class TaskSchedulerTest {

    private static TaskList tasks;

    private static LocalDateTime firstDeadlineStartDate = LocalDateTime.now().plusDays(1);
    private static LocalDateTime secondDeadlineStartDate = LocalDateTime.now().plusDays(2);

    private static LocalDateTime firstEventStartDate = LocalDateTime.now().plusDays(1).plusHours(4);
    private static LocalDateTime firstEventEndDate = LocalDateTime.now().plusDays(1).plusHours(8);

    private static LocalDateTime secondEventStartDate = LocalDateTime.now().plusDays(1).plusHours(9);
    private static LocalDateTime secondEventEndDate = LocalDateTime.now().plusDays(1).plusHours(11);

    private static LocalDateTime thirdEventStartDate = LocalDateTime.now().plusDays(1).plusHours(15);
    private static LocalDateTime thirdEventEndDate = LocalDateTime.now().plusDays(1).plusHours(18);

    private static LocalDateTime eventAtNowStartDate = LocalDateTime.now().minusHours(1);
    private static LocalDateTime eventAtNowEndDate = LocalDateTime.now().plusHours(1);

    private static LocalDateTime eventAtFirstDeadlineStartDate = firstDeadlineStartDate.minusHours(1);
    private static LocalDateTime eventAtFirstDeadlineEndDate = firstDeadlineStartDate.plusHours(1);

    /**
     * Populates the schedule with necessary tasks to carry out the test operations.
     */
    @BeforeAll
    public static void setup() {
        ArrayList<Task> list = new ArrayList<>();
        tasks = new TaskList(list);

        Event firstEvent = new Event("first event", firstEventStartDate, firstEventEndDate);
        tasks.add(firstEvent);

        Event secondEvent = new Event("second event", secondEventStartDate, secondEventEndDate);
        tasks.add(secondEvent);

        Event thirdEvent = new Event("third event", thirdEventStartDate, thirdEventEndDate);
        tasks.add(thirdEvent);
    }

    private static TaskList createNewTaskList() {
        ArrayList<Task> list = new ArrayList<>();
        return new TaskList(list);
    }

    @Test
    public void testTaskSchedulerForEmptySchedule() {
        Long taskDuration = (long) 2;
        String testOutput = TaskScheduler.scheduleByDeadline(tasks, taskDuration, firstDeadlineStartDate);
        String expectedOutput = "You can schedule this task from now till the deadline.\n";
        Assertions.assertTrue(testOutput.contains(expectedOutput));
    }

    @Test
    public void testTaskSchedulerForFilledSchedule() {
        Long taskDuration = (long) 2;
        String testOutput = TaskScheduler.scheduleByDeadline(tasks, taskDuration, secondDeadlineStartDate);
        String expectedOutput = String.format("You can schedule this task from now till %s\n",
                firstEventStartDate.format(DateTimeExtractor.DATE_FORMATTER))
                + String.format("You can schedule this task from %s till %s\n",
                secondEventEndDate.format(DateTimeExtractor.DATE_FORMATTER),
                thirdEventStartDate.format(DateTimeExtractor.DATE_FORMATTER))
                + String.format("You can schedule this task from %s till %s\n",
                thirdEventEndDate.format(DateTimeExtractor.DATE_FORMATTER),
                secondDeadlineStartDate.format(DateTimeExtractor.DATE_FORMATTER));
        Assertions.assertTrue(testOutput.contains(expectedOutput));
    }

    @Test
    public void testSchedulingWithEventAroundNow() {
        Long taskDuration = (long) 2;
        TaskList testTasks = createNewTaskList();
        Event testEvent = new Event("test event", eventAtNowStartDate, eventAtNowEndDate);
        testTasks.add(testEvent);
        String testOutput = TaskScheduler.scheduleByDeadline(testTasks, taskDuration, firstDeadlineStartDate);
        String expectedOutput = String.format("You can schedule this task from %s till %s\n",
                eventAtNowEndDate.format(DateTimeExtractor.DATE_FORMATTER),
                firstDeadlineStartDate.format(DateTimeExtractor.DATE_FORMATTER));
        Assertions.assertTrue(testOutput.contains(expectedOutput));
    }

    @Test
    public void testSchedulingWithEventAroundDeadline() {
        Long taskDuration = (long) 2;
        TaskList testTasks = createNewTaskList();
        Event testEvent = new Event("test event", eventAtFirstDeadlineStartDate,
                eventAtFirstDeadlineEndDate);
        testTasks.add(testEvent);
        String testOutput = TaskScheduler.scheduleByDeadline(testTasks, taskDuration, firstDeadlineStartDate);
        String expectedOutput = String.format("You can schedule this task from now till %s\n",
                eventAtFirstDeadlineStartDate.format(DateTimeExtractor.DATE_FORMATTER));
        Assertions.assertTrue(testOutput.contains(expectedOutput));
    }

    @Test
    public void testTaskSchedulerForNoFreeSlots() {
        Long taskDuration = (long) 29;
        String testOutput = TaskScheduler.scheduleByDeadline(tasks, taskDuration, secondDeadlineStartDate);
        String expectedOutput = "There is no free slot to insert the task. Consider freeing up your schedule.\n";
        Assertions.assertTrue(testOutput.contains(expectedOutput));
    }

    @Test
    public void testForExceededDuration() {
        Long taskDuration = (long) 25;
        String testOutput = TaskScheduler.scheduleByDeadline(tasks, taskDuration, firstDeadlineStartDate);
        String expectedOutput = "The duration is too long to be done within now and the deadline.\n";
        Assertions.assertTrue(testOutput.contains(expectedOutput));
    }

    @Test
    public void testForExceededDurationWithHardLimit() {
        Long taskDuration = (long) 24 * 30 + 1;
        String testOutput = TaskScheduler.scheduleTask(tasks, taskDuration);
        String expectedOutput = "The duration is too long to be done within the next 30 days.\n";
        Assertions.assertTrue(testOutput.contains(expectedOutput));
    }

    @Test
    public void testSchedulingForEmptyScheduleWithHardLimit() {
        Long taskDuration = (long) 2;
        TaskList testTasks = createNewTaskList();
        String testOutput = TaskScheduler.scheduleTask(testTasks, taskDuration);
        String expectedOutput = "You can schedule this task anytime.\n";
        Assertions.assertTrue(testOutput.contains(expectedOutput));
    }

    @Test
    public void testFilledScheduleWithHardLimit() {
        Long taskDuration = (long) 2;
        String testOutput = TaskScheduler.scheduleTask(tasks, taskDuration);
        String expectedOutput = String.format("You can schedule this task from now till %s\n",
                firstEventStartDate.format(DateTimeExtractor.DATE_FORMATTER))
                + String.format("You can schedule this task from %s till %s\n",
                secondEventEndDate.format(DateTimeExtractor.DATE_FORMATTER),
                thirdEventStartDate.format(DateTimeExtractor.DATE_FORMATTER))
                + String.format("You can schedule this task from %s till %s\n",
                thirdEventEndDate.format(DateTimeExtractor.DATE_FORMATTER),
                LocalDateTime.now().plusDays(30).format(DateTimeExtractor.DATE_FORMATTER));
        Assertions.assertTrue(testOutput.contains(expectedOutput));
    }
}
