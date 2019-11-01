package command;

import degree.DegreeManager;
import exception.DukeException;
import org.junit.jupiter.api.Test;
import storage.Storage;
import task.TaskList;
import ui.UI;
import list.DegreeList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BadCommandTest {

    private Command testCommand = new BadCommand("Bad", "");
    private TaskList testTaskList = new TaskList("T | 0 | Send even more Help\n"
            + "R | 0 | Deliver Help | Day\n"
            + "A | 0 | Send less help | Sending Enough\n"
            + "W | 0 | Sleeping | Jan 15th and 25th");
    private UI testUi = new UI();
    private Storage testStorage = new Storage("dummy.txt", "dummydegree.txt");
    private DegreeList testList = new DegreeList();
    private DegreeManager degreesManager = new DegreeManager();

    BadCommandTest() throws DukeException {
    }

    @Test
    void testExecute() {
        try {
            testCommand.execute(testTaskList, testUi, testStorage, testList, this.degreesManager);
        } catch (Exception e) {
            assertEquals("☹ OOPS!!! I'm sorry, but I don't know what that means :-(", e.getMessage());
        }
    }
}