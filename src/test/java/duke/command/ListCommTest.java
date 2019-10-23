package duke.command;

import duke.task.Task;
import duke.task.TaskList;
import duke.task.Todo;
import duke.ui.Ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

//@@author talesrune
class ListCommTest {

    @Test
    void listTest() {
        Ui ui = new Ui();
        TaskList items = new TaskList();
        Task task = new Todo("Hello");
        Task task2 = new Todo("world");
        Command cmd = new AddCommand(task);
        cmd.execute(items, ui);
        cmd = new AddCommand(task2);
        cmd.execute(items, ui);
        Task task3 = new Todo("!");
        cmd = new AddCommand(task3);
        cmd.execute(items, ui);

        cmd = new ListCommand();
        cmd.execute(items, ui);

        Task taskdummy = new Todo("dummy");
        assertEquals("     Here are the tasks in your list:\n"
                + "     1.[T]" + taskdummy.getStatusIconGui() + " Hello\n"
                + "     2.[T]" + taskdummy.getStatusIconGui() + " world\n"
                + "     3.[T]" + taskdummy.getStatusIconGui() + " !\n", cmd.executeGui(items, ui)); // \u2718 or ✗
    }
}
