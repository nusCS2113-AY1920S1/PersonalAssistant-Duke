package duke.logic.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import duke.exception.DukeException;
import duke.extensions.Recurrence;
import duke.logic.parser.EditCommandParser;
import duke.logic.parser.KeywordAndField;
import duke.storage.Storage;
import duke.storage.UndoStack;
import duke.task.Task;
import duke.tasklist.TaskList;
import duke.ui.Ui;

public class EditCommandTest {
    private static final String FILE_PATH = "data/editCommandTest.json";

    private static final Ui ui = new Ui();
    private static final UndoStack undoStack = new UndoStack();
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
    public void execute_allFieldsFilteredList_success() throws DukeException, IOException {
        TaskList tasks = createTaskList();
        EditCommand editCommand;
        editCommand = new EditCommandParser().parse(Optional.of("cs"), "2 -description hello world");
        editCommand.execute(tasks, ui, storage);
        String expectedTaskDescription = "hello world 29/10/2017 00:00";
        String actualTaskDescription = tasks.get(3).getDescription();
        assertEquals(expectedTaskDescription, actualTaskDescription);

        editCommand = new EditCommandParser().parse(Optional.of("cs"), "1 -priority 2");
        editCommand.execute(tasks, ui, storage);
        String expectedTaskPriority = "High";
        String actualTaskPriority = tasks.get(1).getPriority();
        assertEquals(expectedTaskPriority, actualTaskPriority);

        editCommand = new EditCommandParser().parse(Optional.of("cs"), "1 -t 101019");
        editCommand.execute(tasks, ui, storage);
        LocalDateTime expectedTaskDate = LocalDateTime.of(2019, Month.OCTOBER, 10, 0, 0);
        LocalDateTime actualTaskDate = tasks.get(1).getDateTime();
        assertEquals(expectedTaskDate, actualTaskDate);

        editCommand = new EditCommandParser().parse(Optional.of("cs"), "1 -d 2");
        editCommand.execute(tasks, ui, storage);
        String expectedTaskDuration = "2";
        String actualTaskDuration = tasks.get(1).getDuration();
        assertEquals(expectedTaskDuration, actualTaskDuration);

        editCommand = new EditCommandParser().parse(Optional.of("cs"), "1 -r weekly");
        editCommand.execute(tasks, ui, storage);
        String expectedTaskRecurrence = "Weekly";
        String actualTaskRecurrence = tasks.get(1).getRecurrenceCode();
        assertEquals(expectedTaskRecurrence, actualTaskRecurrence);
    }

    @Test
    public void execute_allFieldsUnfilteredList_success() throws DukeException, IOException {
        TaskList tasks = createTaskList();
        EditCommand editCommand;
        editCommand = new EditCommandParser().parse(Optional.empty(), "1 -description hello world");
        editCommand.execute(tasks, ui, storage);
        String expectedTaskDescription = "hello world 29/10/2017 00:00";
        String actualTaskDescription = tasks.get(0).getDescription();
        assertEquals(expectedTaskDescription, actualTaskDescription);

        editCommand = new EditCommandParser().parse(Optional.empty(), "1 -priority 2");
        editCommand.execute(tasks, ui, storage);
        String expectedTaskPriority = "High";
        String actualTaskPriority = tasks.get(0).getPriority();
        assertEquals(expectedTaskPriority, actualTaskPriority);

        editCommand = new EditCommandParser().parse(Optional.empty(), "1 -t 101019");
        editCommand.execute(tasks, ui, storage);
        LocalDateTime expectedTaskDate = LocalDateTime.of(2019, Month.OCTOBER, 10, 0, 0);
        LocalDateTime actualTaskDate = tasks.get(0).getDateTime();
        assertEquals(expectedTaskDate, actualTaskDate);

        editCommand = new EditCommandParser().parse(Optional.empty(), "4 -d 2");
        editCommand.execute(tasks, ui, storage);
        String expectedTaskDuration = "2";
        String actualTaskDuration = tasks.get(3).getDuration();
        assertEquals(expectedTaskDuration, actualTaskDuration);

        editCommand = new EditCommandParser().parse(Optional.empty(), "3 -r weekly");
        editCommand.execute(tasks, ui, storage);
        String expectedTaskRecurrence = "Weekly";
        String actualTaskRecurrence = tasks.get(2).getRecurrenceCode();
        assertEquals(expectedTaskRecurrence, actualTaskRecurrence);
    }


    @Test
    void execute_wrongKeywordInput_failure() throws DukeException {
        TaskList tasks = createTaskList();
        EditCommand editCommand;
        editCommand = new EditCommandParser().parse(Optional.empty(), "1 -desc hello world");
        Exception exception = assertThrows(DukeException.class, () ->
                editCommand.execute(tasks, ui, storage));
        assertEquals("I'm sorry, I do not know what field you are trying to edit!",
                exception.getMessage());
    }

    @Test
    void execute_wrongPriorityInput_failure() throws DukeException {
        TaskList tasks = createTaskList();
        EditCommand editCommand;
        editCommand = new EditCommandParser().parse(Optional.empty(), "1 -priority hello world");
        Exception exception = assertThrows(DukeException.class, () ->
                editCommand.execute(tasks, ui, storage));
        assertEquals("Please enter a numerical field for the priority!", exception.getMessage());
    }

    @Test
    void execute_wrongTimeInput_failure() throws DukeException {
        TaskList tasks = createTaskList();
        EditCommand editCommand;
        editCommand = new EditCommandParser().parse(Optional.empty(), "1 -t hello world");
        Exception exception = assertThrows(DukeException.class, () ->
                editCommand.execute(tasks, ui, storage));
        assertEquals("Please enter a date in ddMMyy HHmm format!", exception.getMessage());
    }

    @Test
    void execute_wrongDurationInput_failure() throws DukeException {
        TaskList tasks = createTaskList();
        EditCommand editCommand;
        editCommand = new EditCommandParser().parse(Optional.empty(), "1 -d hello world");
        Exception exception = assertThrows(DukeException.class, () ->
                editCommand.execute(tasks, ui, storage));
        assertEquals("Please enter a numerical field for the duration!", exception.getMessage());
    }

    @Test
    void execute_wrongRecurrenceInput_failure() throws DukeException {
        TaskList tasks = createTaskList();
        EditCommand editCommand;
        editCommand = new EditCommandParser().parse(Optional.empty(), "1 -r hello world");
        Exception exception = assertThrows(DukeException.class, () ->
                editCommand.execute(tasks, ui, storage));
        assertEquals("Please enter a valid recurrence period!", exception.getMessage());
    }

    @Test
    public void constructor_nonNumericalIndex_failure() throws DukeException {
        Optional<String> cs = Optional.of("cs");
        Exception exception = assertThrows(DukeException.class, () ->
                new EditCommandParser().parse(Optional.empty(), "g -r hello world"));
        assertEquals("Please enter a numerical field for the index!", exception.getMessage());
    }

    @Test
    public void constructor_indexOutOfBoundsTooHigh_failure() throws DukeException {
        TaskList tasks = createTaskList();
        Optional<String> cs = Optional.of("cs");
        EditCommand e =  new EditCommandParser().parse(Optional.empty(), "10 -r hello world");
        Exception exception = assertThrows(DukeException.class, () ->
                e.execute(tasks, ui, storage));
        assertEquals("Please enter a valid task index!", exception.getMessage());
    }
}
