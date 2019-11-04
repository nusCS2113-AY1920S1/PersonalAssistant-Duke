package duke.extensions;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import duke.exception.DukeException;
import duke.logic.AbnormalityChecker;
import duke.storage.Storage;
import duke.task.Event;
import duke.tasklist.TaskList;
import duke.ui.Ui;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import duke.exception.DukeException;
import duke.storage.Storage;
import duke.task.Event;
import duke.tasklist.TaskList;
import duke.ui.Ui;

class AbnormalityCheckerTest {
    private static final String FILE_PATH = "data/editCommandTest.json";

    private static final Ui ui = new Ui();
    private static final Storage storage = new Storage(FILE_PATH);
    private final InputStream systemIn = System.in;
    private final PrintStream systemOut = System.out;

    private ByteArrayInputStream testIn;
    private ByteArrayOutputStream testOut;

    @BeforeEach
    public void setUpOutput() {
        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));
    }

    private void provideInput(String data) {
        testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);
    }

    private String getOutput() {
        return testOut.toString();
    }

    @AfterEach
    public void restoreSystemInputOutput() {
        System.setIn(systemIn);
        System.setOut(systemOut);
    }

    private TaskList createTaskList() throws DukeException {
        TaskList t = new TaskList();

        //Recurrence parameters
        Recurrence recurrenceWeekly = new Recurrence(Optional.of("weekly"));
        Recurrence recurrenceDaily = new Recurrence(Optional.of("daily"));
        Recurrence recurrenceNone = new Recurrence(Optional.empty());

        //Filter parameters
        Optional<String> empty = Optional.empty();

        //Date parameters
        Optional<LocalDateTime> dateTime1 = Optional.of(LocalDateTime.of(2017, Month.OCTOBER, 29,
                0, 0));
        Optional<LocalDateTime> dateTime2 = Optional.of(LocalDateTime.of(2018, Month.OCTOBER, 29,
                0, 0));
        Optional<LocalDateTime> dateTime3 = Optional.of(LocalDateTime.of(2017, Month.FEBRUARY, 10,
                0, 0));

        //Description parameters
        String description1 = "cs2113 assignment";
        String description2 = "cs2113 homework";
        String description3 = "cs2113 homework 2";
        String description4 = "More cs homework";

        t.add(new Event(empty, dateTime1, recurrenceDaily, description1, 4, 1));
        t.add(new Event(Optional.of("cs"), dateTime3, recurrenceDaily, description3, 4, 1));
        t.add(new Event(Optional.of("cs"), dateTime2, recurrenceNone, description2, 5, 1));
        t.add(new Event(empty, dateTime2, recurrenceNone, description4, 5, 1));

        return t;
    }

    @Test
    void checkEventClashTrue() throws DukeException {
        TaskList tasks = createTaskList();
        AbnormalityChecker abnormalityChecker = new AbnormalityChecker(tasks);
        Recurrence recurrenceNone = new Recurrence(Optional.empty());
        Optional<LocalDateTime> dateTime = Optional.of(LocalDateTime.of(2018, Month.OCTOBER, 29,
                0, 0));

        Event clashingEvent = new Event(Optional.empty(), dateTime, recurrenceNone, "abc", 5, 1);
        assertTrue(abnormalityChecker.checkEventClash(clashingEvent));
    }

    @Test
    void checkEventClashFalse() throws DukeException {
        TaskList tasks = createTaskList();
        AbnormalityChecker abnormalityChecker = new AbnormalityChecker(tasks);
        Recurrence recurrenceNone = new Recurrence(Optional.empty());
        Optional<LocalDateTime> dateTime = Optional.of(LocalDateTime.of(2030, Month.OCTOBER, 29,
                0, 0));

        Event clashingEvent = new Event(Optional.empty(), dateTime, recurrenceNone, "abc", 5, 1);
        assertFalse(abnormalityChecker.checkEventClash(clashingEvent));
    }
}
