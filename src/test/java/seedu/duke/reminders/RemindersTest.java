package seedu.duke.reminders;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import seedu.duke.data.Storage;
import seedu.duke.Reminders.Reminders;
import seedu.duke.command.Parser;
import seedu.duke.task.Task;
import seedu.duke.task.TaskList;
import seedu.duke.ui.Ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Test class for deadline.
 */

public class RemindersTest {

    private static Storage storage = new Storage("data/duke.txt");
    private static TaskList list;
    private static Parser parser = new Parser();
    private static Ui ui = new Ui(new Scanner(System.in));

    @Test

    /**
     * Check if oneDay reminder exist.
     */
    public void dummyTest() {
        Reminders reminders = new Reminders();
        reminders.oneDay();
        list = new TaskList(storage.load());
        list = parser.parse("deadline assignment/by 19/9/2019", list);

        try {
            storage.save(list.return_list());
        } catch (IOException e) {
            ui.show_save_error();
        }

        assertEquals(true, reminders.checkList(reminders.oneDay()));
    }
}
