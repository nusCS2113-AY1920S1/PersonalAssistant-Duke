package duke.command;

import duke.task.Task;
import duke.task.TaskList;
import duke.task.Todo;
import duke.ui.Ui;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

//@@author talesrune
class DoneCommTest {

    @Test
    void doneTest() {
        Ui ui = new Ui();
        TaskList items = new TaskList();
        Task task = new Todo("walk");
        Command cmd = new AddCommand(task);
        cmd.execute(items, ui);
        assertEquals("[X]", items.get(items.size() - 1).getStatusIcon());
        cmd = new DoneCommand(0);
        cmd.execute(items,ui);
        assertEquals("[/]", items.get(items.size() - 1).getStatusIcon());
    }
}