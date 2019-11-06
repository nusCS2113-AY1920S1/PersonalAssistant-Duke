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
class FilterCommTest {

    @Test
    void filterTest() throws DukeException {
        TaskList items = new TaskList();
        Task task = new Todo("walk");
        items.add(task);
        task = new Deadline("homework", "07/07/2017 1707");
        items.add(task);
        task = new Deadline("movie", "05/05/2015 1500");
        items.add(task);
        Command cmd = new FilterCommand("todo");
        Ui ui = new Ui();
        Task taskdummy = new Todo("dummy");

        assertEquals("     Here are the filtered tasks in your list:\n"
                + "     1.[T]" + taskdummy.getStatusIconGui()
                + " walk\n", cmd.executeGui(items,ui));
        cmd = new FilterCommand("deadline");
        assertEquals("     Here are the filtered tasks in your list:\n"
                + "     2.[D]" + taskdummy.getStatusIconGui()
                + " homework (by: 7th of July 2017, 5:07 PM)\n"
                + "     3.[D]" + taskdummy.getStatusIconGui()
                + " movie (by: 5th of May 2015, 3PM)\n", cmd.executeGui(items,ui));
    }
}