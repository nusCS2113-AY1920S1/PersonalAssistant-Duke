package duke.command;

import duke.task.TaskList;
import duke.ui.Ui;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

//@@author gervaiseang
/**
 * A test class to test the correctness of HelpCommand to open the help window.
 */

public class HelpCommTest {
    @Test
    void helpTest() {
        TaskList items = new TaskList();
        Ui ui = new Ui();
        HelpCommand help = new HelpCommand();
        help.executeGui(items, ui);

        assertTrue(help instanceof HelpCommand);
    }
}