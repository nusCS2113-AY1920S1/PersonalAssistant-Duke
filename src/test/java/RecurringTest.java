import duke.storage.Storage;
import duke.tasklist.TaskList;
import duke.ui.Ui;
import org.junit.jupiter.api.Test;

class RecurringTest {
    private Ui ui = new Ui();
    private Storage storage = new Storage("data/duke.json");
    private TaskList tasks = new TaskList();

    String description = "homework -r weekly";
    String taskType = "todo";

    @Test
    void getRecurrencePeriod() {
        Recurring recurring = new Recurring(description, taskType, tasks, ui, storage);
        assertEquals("WEEKLY", recurring.getRecurrencePeriod());
    }
}