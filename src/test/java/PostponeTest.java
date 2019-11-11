import chronologer.command.PostponeCommand;
import chronologer.exception.ChronologerException;
import chronologer.parser.ParserFactory;
import chronologer.storage.ChronologerStateList;
import chronologer.storage.Storage;
import chronologer.task.Deadline;
import chronologer.task.Event;
import chronologer.task.Task;
import chronologer.task.TaskList;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Test class for postpone commands.
 *
 * @author Tan Yi Xiang
 * @version V1.0
 */
public class PostponeTest {

    private static TaskList tasks;
    private static File file;
    private static File placeholder;
    private static Storage storage;
    private static ChronologerStateList history;

    @BeforeAll
    static void setup() {
        ArrayList<Task> testList = new ArrayList<Task>();
        tasks = new TaskList(testList);
        file = new File(System.getProperty("user.dir") + "/src/test/PostponeList");
        placeholder = new File(System.getProperty("user.dir") + "/src/test/States");
        storage = new Storage(file);
        history = new ChronologerStateList(placeholder, placeholder, placeholder);
    }


    @Test
    void testPostpone() throws ChronologerException {
        Task deadlineTest = new Deadline("Deadline test", LocalDateTime.of(2001, 1, 1, 1, 0));
        Task eventTest = new Event("Event test", LocalDateTime.of(2002, 2, 2, 2, 0),
            LocalDateTime.of(2002, 3, 3, 3, 0));
        Task deadlineCLashTest = new Deadline("Deadline test", LocalDateTime.of(2019, 12, 12,
            19, 0));

        tasks.add(deadlineTest);
        tasks.add(eventTest);
        tasks.add(deadlineCLashTest);

        PostponeCommand command = new PostponeCommand(0, LocalDateTime.of(2004, 4, 4, 4, 0));
        command.execute(tasks, storage, history);
        Assertions.assertEquals(deadlineTest.getStartDate(), LocalDateTime.of(2004, 4, 4, 4, 0));

        command = new PostponeCommand(1, LocalDateTime.of(2004, 4, 4, 4, 0),
            LocalDateTime.of(2005, 5, 5, 5, 0));
        command.execute(tasks, storage, history);
        Assertions.assertEquals(eventTest.getStartDate(), LocalDateTime.of(2004, 4, 4, 4, 0));
        Assertions.assertEquals(eventTest.getEndDate(), LocalDateTime.of(2005, 5, 5, 5, 0));
    }

    @Test
    void testClash() {
        PostponeCommand command = new PostponeCommand(3, LocalDateTime.of(2004, 4, 4, 4, 0));
        Assertions.assertThrows(ChronologerException.class, () -> {
            command.execute(tasks, storage, history);
        });
    }

    @Test
    void testPostponeDateEarly() {
        PostponeCommand command = new PostponeCommand(3, LocalDateTime.of(2002, 2, 2, 2, 0));
        Assertions.assertThrows(ChronologerException.class, () -> {
            command.execute(tasks, storage, history);
        });
    }

    @Test
    void testError() {
        Assertions.assertThrows(ChronologerException.class, () -> {
            ParserFactory.parse("postpone");
        });
        Assertions.assertThrows(ChronologerException.class, () -> {
            ParserFactory.parse(("postpone -1 24/11/2017"));
        });
        Assertions.assertThrows(ChronologerException.class, () -> {
            ParserFactory.parse(("postpone 2 24/11/2017 - "));
        });
        Assertions.assertThrows(ChronologerException.class, () -> {
            ParserFactory.parse(("postpone 1"));
        });
        Assertions.assertThrows(ChronologerException.class, () -> {
            ParserFactory.parse(("postpone 1 0"));
        });
    }

    @AfterAll
    static void teardownSetup() {
        assert file.delete();
    }
}
