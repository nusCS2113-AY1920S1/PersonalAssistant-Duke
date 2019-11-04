package duke.logic.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import duke.exception.DukeException;
import duke.extensions.Recurrence;
import duke.storage.Storage;
import duke.task.Task;
import duke.tasklist.TaskList;
import duke.ui.Ui;

class AutoAssignCommandTest {

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

        t.add(new Task(empty, dateTime1, recurrenceDaily, description1, 4,1));
        t.add(new Task(Optional.of("cs"), dateTime3, recurrenceDaily, description3, 4,1));
        t.add(new Task(Optional.of("cs"), dateTime2, recurrenceNone, description2, 5,1));
        t.add(new Task(empty, dateTime2, recurrenceNone, description4, 5,1));

        return t;
    }

    @Test
    public void execute_autoAssignFilterName_success() throws DukeException, IOException {
        TaskList tasks = createTaskList();
        AutoAssignCommand autoAssignCommand = new AutoAssignCommand("4");
        final String input = "Y";
        provideInput(input);
        autoAssignCommand.execute(tasks, ui, storage);
        Optional<String> expectedTaskFilter = Optional.of("cs");
        Optional<String> actualTaskFilter = tasks.get(3).getFilter();
        assertEquals(expectedTaskFilter.get(), actualTaskFilter.get());
    }

    @Test
    public void execute_autoAssignFilterNameReject_success() throws DukeException, IOException {
        TaskList tasks = createTaskList();
        AutoAssignCommand autoAssignCommand = new AutoAssignCommand("4");
        final String input = "N";
        provideInput(input);
        autoAssignCommand.execute(tasks, ui, storage);
        Optional<String> actualTaskFilter = tasks.get(3).getFilter();
        assertFalse(actualTaskFilter.isPresent());
    }

    @Test
    public void execute_autoAssignCosineSimilarity_success() throws DukeException, IOException {
        TaskList tasks = createTaskList();
        AutoAssignCommand autoAssignCommand = new AutoAssignCommand("1");
        final String input = "Y";
        provideInput(input);
        autoAssignCommand.execute(tasks, ui, storage);
        Optional<String> expectedTaskFilter = Optional.of("cs");
        Optional<String> actualTaskFilter = tasks.get(0).getFilter();
        assertEquals(expectedTaskFilter.get(), actualTaskFilter.get());
    }

    @Test
    public void execute_autoAssignRejectFilterAcceptCosine_success() throws DukeException, IOException {
        TaskList tasks = createTaskList();
        AutoAssignCommand autoAssignCommand = new AutoAssignCommand("4");
        final String input = "N\nY";
        provideInput(input);
        autoAssignCommand.execute(tasks, ui, storage);
        Optional<String> expectedTaskFilter = Optional.of("cs");
        Optional<String> actualTaskFilter = tasks.get(3).getFilter();
        assertEquals(expectedTaskFilter.get(), actualTaskFilter.get());
    }
}
