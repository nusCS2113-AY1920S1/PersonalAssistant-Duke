package duke.task;

import duke.command.Command;
import duke.command.DuplicateFoundCommand;
import duke.ui.Ui;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

//@@author e0318465
/**
 * Tests to ensure that detect duplicates works.
 */
public class DetectDuplicateTest {
    @Test
    void detectDuplicateTest() {
        Ui ui = new Ui();
        TaskList items = new TaskList();
        Task task1 = new Todo("homework");
        items.add(task1);

        DetectDuplicate detectDuplicate = new DetectDuplicate(items);
        Command cmd = new DuplicateFoundCommand();
        Task task2 = new Todo("homework");
        items.add(task2);
        if (detectDuplicate.isDuplicate("homework")) {
            assertEquals("     The same task is already in the list!", cmd.executeGui(items,ui));
        }
        Task task3 = new Todo("project work");
        items.add(task3);
        if (detectDuplicate.isDuplicate("project work")) {
            assertEquals("[T][X] project work", task3.toString());
        }
    }
}