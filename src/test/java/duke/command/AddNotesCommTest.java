package duke.command;

import duke.task.Deadline;
import duke.task.Task;
import duke.task.TaskList;
import duke.task.Todo;
import duke.ui.Ui;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import duke.dukeexception.DukeException;

//@@author talesrune
class AddNotesCommTest {

    @Test
    void addNotesTest() throws DukeException {
        TaskList items = new TaskList();
        Task task = new Todo("walk");
        items.add(task);
        task = new Deadline("homework", "07/07/2017 1707");
        items.add(task);
        Command cmd = new AddNotesCommand("5 km",0);
        Ui ui = new Ui();

        assertEquals("     Nice! Added/Updated notes of this task ^^:\n"
                + "       1.[T][X] walk\n"
                + "      | Added Notes: 5 km", cmd.executeGui(items,ui));
        cmd = new AddNotesCommand("cs2113 project", 1);
        assertEquals("     Nice! Added/Updated notes of this task ^^:\n"
                + "       2.[D][X] homework (by: 7th of July 2017, 5:07 PM)\n"
                + "      | Added Notes: cs2113 project", cmd.executeGui(items,ui));
    }
}