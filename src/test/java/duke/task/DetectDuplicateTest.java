package duke.task;

import duke.command.Command;
import duke.command.DuplicateFoundCommand;
import duke.ui.Ui;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DetectDuplicateTest {
    @Test
    void detectDuplicateTest() {
        Ui ui = new Ui();
        TaskList items = new TaskList();
        Task task = new Todo("homework");
        items.add(task);

        DetectDuplicate detectDuplicate = new DetectDuplicate(items);
        if (detectDuplicate.isDuplicate("todo", "homework")) {
            Command cmd = new DuplicateFoundCommand();
            assertEquals("     The same task is already in the list!", cmd.executeGui(items,ui));
        }
    }
}