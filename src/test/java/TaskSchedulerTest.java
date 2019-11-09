import chronologer.TaskScheduler;
import chronologer.parser.DateTimeExtractor;
import chronologer.task.Task;
import chronologer.task.Event;
import chronologer.task.TaskList;
import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class TaskSchedulerTest {

    private static TaskList tasks;

    private ByteArrayOutputStream outContent;
    private PrintStream originalOut;

    private LocalDateTime firstDeadlineStartDate = LocalDateTime.now().plusDays(1);
    private LocalDateTime secondDeadlineStartDate = LocalDateTime.now().plusDays(2);

    private static LocalDateTime firstEventStartDate = LocalDateTime.now().plusDays(1).plusHours(4);
    private static LocalDateTime firstEventEndDate = LocalDateTime.now().plusDays(1).plusHours(8);

    private static LocalDateTime secondEventStartDate = LocalDateTime.now().plusDays(1).plusHours(9);
    private static LocalDateTime secondEventEndDate = LocalDateTime.now().plusDays(1).plusHours(11);

    private static LocalDateTime thirdEventStartDate = LocalDateTime.now().plusDays(1).plusHours(15);
    private static LocalDateTime thirdEventEndDate = LocalDateTime.now().plusDays(1).plusHours(18);

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

    /**
     * Sets up the output stream to read the UI/CLI messages before each test.
     */
    @BeforeEach
    public void setUpStreams() {
        originalOut = System.out;
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
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
    public void testTaskSchedulerForNoFreeSlots() {
        Long taskDuration = (long) 29;
        String testOutput = TaskScheduler.scheduleByDeadline(tasks, taskDuration, secondDeadlineStartDate);
        String expectedOutput = "There is no free slot to insert the task. Consider freeing up your schedule.\n";
        Assertions.assertTrue(testOutput.contains(expectedOutput));
    }

}
