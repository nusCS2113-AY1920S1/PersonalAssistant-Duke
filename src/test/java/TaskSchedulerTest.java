import chronologer.TaskScheduler;
import chronologer.storage.Storage;
import chronologer.task.*;
import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class TaskSchedulerTest {

    private static ArrayList<Task> list;
    private static TaskList tasks;
    private static File file;
    private static Storage storage;

    private ByteArrayOutputStream outContent;
    private PrintStream originalOut;

    private LocalDateTime firstDeadlineStartDate = LocalDateTime.now().plusDays(1);
    LocalDateTime secondDeadlineStartDate = LocalDateTime.now().plusDays(2);

    /**
     * Setups the necessary base to carry out the test operations.
     */
    @BeforeAll
    public static void setup() {
        list = new ArrayList<>();
        tasks = new TaskList(list);
        file = new File(System.getProperty("user.dir") + "/src/test/ArrayList");
        storage = new Storage(file);

        LocalDateTime firstEventStartDate = LocalDateTime.now().plusDays(1).plusHours(4);
        LocalDateTime firstEventEndDate = LocalDateTime.now().plusDays(1).plusHours(8);
        Event firstEvent = new Event("first event", firstEventStartDate, firstEventEndDate);
        tasks.add(firstEvent);

        LocalDateTime secondEventStartDate = LocalDateTime.now().plusDays(1).plusHours(9);
        LocalDateTime secondEventEndDate = LocalDateTime.now().plusDays(1).plusHours(11);
        Event secondEvent = new Event("second event", secondEventStartDate, secondEventEndDate);
        tasks.add(secondEvent);

        LocalDateTime thirdEventStartDate = LocalDateTime.now().plusDays(1).plusHours(15);
        LocalDateTime thirdEventEndDate = LocalDateTime.now().plusDays(1).plusHours(18);
        Event thirdEvent = new Event("third event", thirdEventStartDate, thirdEventEndDate);
        tasks.add(thirdEvent);
    }

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
        TaskScheduler.scheduleByDeadline(tasks, taskDuration, firstDeadlineStartDate);
        String test = outContent.toString();
        String expected = "_______________________________\n\r\n" +
                "You can schedule this task from now till the deadline.\n\r\n" +
                "_______________________________\n\r\n";
        Assertions.assertEquals(expected, test);
    }


}
