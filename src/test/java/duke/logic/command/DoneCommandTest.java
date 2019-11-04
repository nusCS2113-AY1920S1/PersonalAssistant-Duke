package duke.logic.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Optional;

import duke.exception.DukeException;
import duke.extensions.Recurrence;
import duke.storage.Storage;
import duke.task.Task;
import duke.tasklist.TaskList;
import duke.ui.Ui;
import org.junit.jupiter.api.Test;

class DoneCommandTest {
    private static final String FILE_PATH = "data/editCommandTest.json";

    private static final Ui ui = new Ui();
    private static final Storage storage = new Storage(FILE_PATH);

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
        t.add(new Task(empty, dateTime1, recurrenceDaily, description1, 4,1));
        t.add(new Task(cs, dateTime3, recurrenceDaily, description3, 4,1));
        t.add(new Task(empty, dateTime2, recurrenceNone, description2, 5,1));
        t.add(new Task(cs, dateTime1, recurrenceWeekly, description3, 4,1));

        return t;
    }

    @Test
    public void execute_filteredDone_success() throws DukeException, IOException {
        TaskList tasks = createTaskList();
        DoneCommand doneCommand = new DoneCommand(Optional.of("cs"), "1");
        doneCommand.execute(tasks, ui, storage);
        String expectedStatusIcon = "Y";
        String actualStatusIcon = tasks.get(1).getStatusIcon();
        assertEquals(expectedStatusIcon, actualStatusIcon);
    }

    @Test
    public void execute_done_success() throws DukeException, IOException {
        TaskList tasks = createTaskList();
        DoneCommand doneCommand = new DoneCommand(Optional.empty(), "1");
        doneCommand.execute(tasks, ui, storage);
        String expectedStatusIcon = "Y";
        String actualStatusIcon = tasks.get(0).getStatusIcon();
        assertEquals(expectedStatusIcon, actualStatusIcon);
    }

    @Test
    public void constructor_nonNumericalIndex_failure() {
        Optional<String> cs = Optional.of("cs");
        Exception exception = assertThrows(DukeException.class, () ->
                new DoneCommand(cs, "non"));
        assertEquals("Please enter a numerical field for the index!", exception.getMessage());
    }
}
