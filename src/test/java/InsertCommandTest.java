import duke.exception.DukeException;
import duke.extensions.Recurrence;
import duke.logic.command.InsertCommand;
import duke.storage.Storage;
import duke.task.Task;
import duke.tasklist.TaskList;
import duke.ui.Ui;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class InsertCommandTest {
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
        list.add(new Task(haveFilter, dateTime1, haveRecurrence, description1, 2,1));// diff duration
        list.add(new Task(noFilter, dateTime1, haveRecurrence, description1, 1,1)); // diff filter
        list.add(new Task(haveFilter, dateTime1, haveRecurrence, description2, 1,1)); // diff description
        list.add(new Task(haveFilter, dateTime2, haveRecurrence, description1, 1,1)); // diff datetime
        list.add(new Task(haveFilter, dateTime1, noRecurrence, description1, 1,1)); // diff recurrence

        return list;
    }

    @Test
    void execute_taskInsertedCorrectly_success() throws DukeException, IOException, ParseException {
        TaskList list = createTaskList();
        InsertCommand insertCommand;
        Task baseTask = list.get(1);
        Task newTask = new Task(baseTask);
        Optional<String> noFilter = Optional.empty();

        // insert at correct location
        insertCommand = new InsertCommand(noFilter, 3, baseTask);
        Task expectedTask = baseTask;
        Task previousTask = list.get(noFilter, 3);
        insertCommand.execute(list, ui, storage);
        Task actualTask = list.get(noFilter, 3);
        assertEquals(expectedTask, actualTask);
        assertNotEquals(expectedTask, previousTask);
    }
}
