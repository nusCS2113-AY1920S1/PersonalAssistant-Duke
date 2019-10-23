package duke.command;

import duke.task.Task;
import duke.task.TaskList;
import duke.task.Todo;
import duke.ui.Ui;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

//@@author talesrune
class FindCommTest {

    @Test
    void findTest() {
        Ui ui = new Ui();
        TaskList items = new TaskList();
        Task task = new Todo("walk");
        Task task2 = new Todo("wale");

        Command cmd = new AddCommand(task);
        cmd.execute(items, ui);
        cmd = new AddCommand(task2);
        cmd.execute(items, ui);
        Task task3 = new Todo("cake");
        cmd = new AddCommand(task3);
        cmd.execute(items, ui);

        Task taskdummy = new Todo("dummy");
        cmd = new FindCommand("wal");
        cmd.execute(items,ui);
        assertEquals("     Here are the matching tasks in your list:\n"
                + "     1.[T]" + taskdummy.getStatusIconGui() + " walk\n"
                + "     2.[T]" + taskdummy.getStatusIconGui() + " wale\n", cmd.executeGui(items,ui));

        cmd = new FindCommand("a");
        cmd.execute(items,ui);
        assertEquals("     Here are the matching tasks in your list:\n"
                + "     1.[T]" + taskdummy.getStatusIconGui() + " walk\n"
                + "     2.[T]" + taskdummy.getStatusIconGui() + " wale\n"
                + "     3.[T]" + taskdummy.getStatusIconGui() + " cake\n", cmd.executeGui(items,ui));

    }
}