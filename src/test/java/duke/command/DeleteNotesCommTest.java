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
class DeleteNotesCommTest {

    @Test
    void deleteNotesTest() throws DukeException {
        TaskList items = new TaskList();
        Task task = new Todo("walk");
        task.setNotes("5 km");
        items.add(task);
        task = new Deadline("homework", "07/07/2017 1707");
        task.setNotes("cs2113 project");
        items.add(task);
        Command cmd = new DeleteNotesCommand(0);
        Ui ui = new Ui();

        assertEquals("     Deleted notes of this task ^^:\n"
                + "       1.[T][X] walk\n"
                + "      | Deleted notes: 5 km", cmd.executeGui(items,ui));
        cmd = new DeleteNotesCommand(1);
        assertEquals("     Deleted notes of this task ^^:\n"
                + "       2.[D][X] homework (by: 7th of July 2017, 5:07 PM)\n"
                + "      | Deleted notes: cs2113 project", cmd.executeGui(items,ui));
    }
}