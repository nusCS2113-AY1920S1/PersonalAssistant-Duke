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
class ShowNotesCommTest {

    @Test
    void showNotesTest() throws DukeException {
        TaskList items = new TaskList();
        Task task = new Todo("walk");
        task.setNotes("5 km");
        items.add(task);
        task = new Deadline("homework", "07/07/2017 1707");
        task.setNotes("cs2113 project");
        items.add(task);
        Command cmd = new ShowNotesCommand(0);
        Ui ui = new Ui();

        assertEquals("     Here is the task and its notes:\n"
                + "       1.[T][X] walk\n"
                + "      | Notes: 5 km", cmd.executeGui(items,ui));
        cmd = new ShowNotesCommand(1);
        assertEquals("     Here is the task and its notes:\n"
                + "       2.[D][X] homework (by: 7th of July 2017, 5:07 PM)\n"
                + "      | Notes: cs2113 project", cmd.executeGui(items,ui));
    }
}