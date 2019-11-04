package duke.logic.command;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Optional;

import duke.exception.DukeException;
import duke.extensions.Recurrence;
import duke.storage.Storage;
import duke.task.Task;
import duke.tasklist.TaskList;
import duke.ui.Ui;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FindCommandTest {
    private static final String FILE_PATH = "data/editCommandTest.json";
    private static final Ui ui = new Ui();
    private static final Storage storage = new Storage(FILE_PATH);

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    public void setUpOutput() {
        System.setOut(new PrintStream(outContent));
    }

    public void restoreStreams() {
        System.setOut(originalOut);
    }

    private TaskList createTaskList() throws DukeException {
        TaskList t = new TaskList();

        //Recurrence parameters
        Recurrence recurrenceWeekly = new Recurrence(Optional.of("weekly"));
        Recurrence recurrenceDaily = new Recurrence(Optional.of("daily"));
        Recurrence recurrenceNone = new Recurrence(Optional.empty());

        //Filter parameters
        Optional<String> cs = Optional.of("cs");
        Optional<String> empty = Optional.empty();

        //Date parameters
        Optional<LocalDateTime> dateTime1 = Optional.of(LocalDateTime.of(2017, Month.OCTOBER, 29,
                0, 0));
        Optional<LocalDateTime> dateTime2 = Optional.of(LocalDateTime.of(2018, Month.OCTOBER, 29,
                0, 0));
        Optional<LocalDateTime> dateTime3 = Optional.of(LocalDateTime.of(2017, Month.FEBRUARY, 10,
                0, 0));

        //Description parameters
        String description1 = "cs2113 is the best :')";
        String description2 = "cg2271 is the best :')";
        String description3 = "st2334 is the best :')";
        t.add(new Task(empty, dateTime1, recurrenceDaily, description1, 4));
        t.add(new Task(cs, dateTime3, recurrenceDaily, description3, 4));
        t.add(new Task(empty, dateTime2, recurrenceNone, description2, 5));
        t.add(new Task(cs, dateTime1, recurrenceWeekly, description3, 4));

        return t;
    }

    @Test
    public void execute_findTestContainsCorrectFind_success() throws DukeException, IOException, ParseException {
        TaskList t = createTaskList();
        Optional<String> noFilter = Optional.empty();
        setUpOutput();
        FindCommand findCommand = new FindCommand("cs2113", noFilter);
        findCommand.execute(t, ui, storage);
        assertTrue(outContent.toString().contains("cs2113 is the best :') 29/10/2017 00:00"));
    }

    @Test
    public void execute_findTestContainsWrongFind_failure() throws DukeException, IOException, ParseException {
        TaskList t = createTaskList();
        Optional<String> noFilter = Optional.empty();
        setUpOutput();
        FindCommand findCommand = new FindCommand("cs2113", noFilter);
        findCommand.execute(t, ui, storage);
        assertTrue(!outContent.toString().contains("cg2271 is the best :') 29/10/2017 00:00"));
    }

    @Test
    public void execute_findTestNoTaskFound_sucess() throws DukeException, IOException, ParseException {
        TaskList t = createTaskList();
        Optional<String> noFilter = Optional.empty();
        setUpOutput();
        FindCommand findCommand = new FindCommand("LAS1201", noFilter);
        findCommand.execute(t, ui, storage);
        assertTrue(outContent.toString().contains("I'm sorry we found no tasks matching that description!"));
    }
}
