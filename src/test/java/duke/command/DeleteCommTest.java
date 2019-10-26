package duke.command;

import duke.task.Task;
import duke.task.TaskList;
import duke.task.Todo;
import duke.ui.Ui;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.Test;

//@@author talesrune
class DeleteCommTest {

    @Test
    void deleteTest() {
        Ui ui = new Ui();
        TaskList items = new TaskList();
        Task task = new Todo("sleep");
        Task task2 = new Todo("wake up");
        Command cmd = new AddCommand(task);
        cmd.execute(items, ui);
        cmd = new AddCommand(task2);
        cmd.execute(items, ui);
        cmd = new DeleteCommand(0);
        cmd.execute(items, ui);

        for (int i = 0; i < items.size(); i++) {
            assertNotEquals(task,items.get(i));
        }
    }
}