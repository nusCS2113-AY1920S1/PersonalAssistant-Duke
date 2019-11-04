import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import duke.logic.command.AddCommand;
import duke.logic.command.Command;
import duke.logic.command.DeleteCommand;
import duke.logic.command.UndoCommand;
import duke.exception.DukeException;
import duke.extensions.Recurrence;
import duke.storage.Storage;
import duke.storage.UndoStack;
import duke.task.Task;
import duke.tasklist.TaskList;
import duke.ui.Ui;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Optional;

class UndoCommandTest {
    private static final String FILE_PATH = "data/duke-test.json";

    private static final Ui ui = new Ui();
    private static final Storage storage = new Storage(FILE_PATH);

    private static TaskList createTaskList() throws DukeException {
        TaskList list = new TaskList();

        //Recurrence parameters
        Recurrence haveRecurrence = new Recurrence(Optional.of("daily"));
        Recurrence noRecurrence = new Recurrence(Optional.empty());

        //Filter parameters
        Optional<String> haveFilter = Optional.of("filter");
        Optional<String> noFilter = Optional.empty();

        System.out.println(Integer.parseInt("-----5"));
        //Date parameters
        Optional<LocalDateTime> dateTime1 = Optional.of(LocalDateTime.of(2017, Month.OCTOBER,
                29, 0, 0));
        Optional<LocalDateTime> dateTime2 = Optional.of(LocalDateTime.of(2018, Month.OCTOBER,
                29, 0, 0));

        //Description parameters
        String description1 = "description1";
        String description2 = "description2";

        //Create different Tasks to use as template for testing
        list.add(new Task(haveFilter, dateTime1, haveRecurrence, description1, 1,1)); // base
        list.add(new Task(haveFilter, dateTime1, haveRecurrence, description1, 2,1)); // diff duration
        list.add(new Task(noFilter, dateTime1, haveRecurrence, description1, 1,1)); // diff filter
        list.add(new Task(haveFilter, dateTime1, haveRecurrence, description2, 1,1)); // diff description
        list.add(new Task(haveFilter, dateTime2, haveRecurrence, description1, 1,1)); // diff datetime
        list.add(new Task(haveFilter, dateTime1, noRecurrence, description1, 1,1)); // diff recurrence

        return list;
    }

    @Test
    public void execute_allUndoTypes_success() throws DukeException, IOException, ParseException {
        TaskList list = createTaskList();
        UndoStack undoStack = new UndoStack();
        UndoCommand undoCommand = new UndoCommand(undoStack);
        Optional<String> noFilter = Optional.empty();
        Command testCommand;
        Task expectedTask;
        Task actualTask;
        Task prevTask;

        // undo AddCommand
        testCommand = new AddCommand(noFilter, Optional.empty(), Optional.empty(), "undo this", "task", 0, 1);
        expectedTask = list.get(noFilter, 6);
        testCommand.savePrevState(list, undoStack);
        testCommand.execute(list, ui, storage);
        prevTask = list.get(noFilter, 7);
        undoCommand.execute(list, ui, storage);
        actualTask = list.get(noFilter, 6);
        assertEquals(expectedTask, actualTask);
        assertNotEquals(prevTask, actualTask);

        // undo DeleteCommand
        testCommand = new DeleteCommand(noFilter, "4");
        expectedTask = list.get(noFilter, 4);
        testCommand.savePrevState(list, undoStack);
        testCommand.execute(list, ui, storage);
        prevTask = list.get(noFilter, 4);
        undoCommand.execute(list, ui, storage);
        actualTask = list.get(noFilter, 4);
        assertEquals(expectedTask, actualTask);
        assertNotEquals(prevTask, actualTask);

		/*
		// undo EditCommand
		testCommand = new EditCommandParser().parse(noFilter, "4 -priority 2");
		expectedTask = list.get(noFilter, 4);
		testCommand.savePrevState(list, undoStack);
		testCommand.execute(list, ui, storage);
		prevTask = list.get(noFilter, 4);
		undoCommand.execute(list, ui, storage);
		actualTask = list.get(noFilter, 4);
		assertEquals(expectedTask, actualTask);
		assertNotEquals(prevTask, actualTask);



		// undo DoneCommand
		testCommand = new DoneCommand(noFilter, "4");
		expectedTask = list.get(noFilter, 4);
		testCommand.savePrevState(list, undoStack);
		testCommand.execute(list, ui, storage);
		prevTask = list.get(noFilter, 4);
		undoCommand.execute(list, ui, storage);
		actualTask = list.get(noFilter, 4);
		assertEquals(expectedTask, actualTask);
		assertNotEquals(prevTask, actualTask);
		 */
    }
}
