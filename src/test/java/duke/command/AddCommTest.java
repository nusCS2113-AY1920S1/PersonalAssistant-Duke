package duke.command;

import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;
import duke.task.TaskList;
import duke.task.Todo;
import duke.ui.Ui;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import java.text.ParseException;

//@@author talesrune
class AddCommTest {

    @Test
    void addTest() throws ParseException {
        Ui ui = new Ui();
        TaskList items = new TaskList();
        Task task = new Todo("sleep");
        Command cmd = new AddCommand(task);
        cmd.execute(items, ui);
        assertEquals(task, items.get(items.size() - 1));

        task = new Deadline("sleep", "05/05/2015 1805");
        cmd = new AddCommand(task);
        cmd.execute(items, ui);
        assertEquals(task, items.get(items.size() - 1));

        task = new Event("sleep", "05/05/2015 1800");
        cmd = new AddCommand(task);
        cmd.execute(items, ui);
        assertEquals(task, items.get(items.size() - 1));
    }
}