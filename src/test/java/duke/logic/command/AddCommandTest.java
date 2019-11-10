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


class AddCommandTest {
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
        t.add(new Task(empty, dateTime1, recurrenceDaily, description1, 4, "l"));
        t.add(new Task(cs, dateTime3, recurrenceDaily, description3, 4, "l"));
        t.add(new Task(empty, dateTime2, recurrenceNone, description2, 5, "l"));
        t.add(new Task(cs, dateTime1, recurrenceWeekly, description3, 4, "l"));

        return t;
    }

    @Test
    public void execute_addCommandTask_success() throws DukeException, IOException {
        TaskList tasks = createTaskList();

        //Parameters of new task
        Optional<LocalDateTime> dateTime = Optional.of(LocalDateTime.of(2017, Month.OCTOBER, 29,
                0, 0));
        Recurrence recurrenceWeekly = new Recurrence(Optional.of("weekly"));
        AddCommand addCommand = new AddCommand(Optional.of("cs"), dateTime, Optional.of("weekly"), "tower",
                "task", 2, "l");

        addCommand.execute(tasks, ui, storage);
        String expectedDescriptionOfTask = "tower 29/10/2017 00:00";
        String actualTaskDescription = tasks.get(4).getDescription();
        assertEquals(expectedDescriptionOfTask, actualTaskDescription);
    }

    @Test
    public void execute_addCommandEvent_success() throws DukeException, IOException {
        TaskList tasks = createTaskList();

        //Parameters of new task/
        Optional<LocalDateTime> dateTime = Optional.of(LocalDateTime.of(2017, Month.OCTOBER, 29,
                0, 0));
        AddCommand addCommand = new AddCommand(Optional.of("cs"), dateTime, Optional.of("weekly"), "tower",
                "event", 2, "l");

        addCommand.execute(tasks, ui, storage);
        String expectedStringOfTask = "[R][E][N] tower (29/10/2017 00:00)";
        String actualTaskString = tasks.get(4).toString();
        assertEquals(expectedStringOfTask, actualTaskString);
    }

    @Test
    public void execute_addCommandEventNoDeadline_failure() throws DukeException {
        TaskList tasks = createTaskList();
        //Parameters of new task
        AddCommand addCommand = new AddCommand(Optional.of("cs"), Optional.empty(), Optional.of("weekly"), "tower",
                "event", 2, "l");

        Exception exception = assertThrows(DukeException.class, () ->
                addCommand.execute(tasks, ui, storage));
        assertEquals("Your event needs to have a starting time.", exception.getMessage());
    }
}

