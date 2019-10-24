import gazeeebo.TriviaManager.TriviaManager;
import gazeeebo.UI.Ui;
import gazeeebo.commands.tasks.DeadlineCommand;
import gazeeebo.exception.DukeException;
import gazeeebo.storage.Storage;
import gazeeebo.tasks.Task;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GazeeeboTest {

    @Test
    public void test() throws IOException {
        DeadlineCommand deadlineCommand = new DeadlineCommand();
        ArrayList<Task> tasks = new ArrayList<Task>();
        Ui ui = new Ui();
        Storage storage = new Storage();
        TriviaManager triviaManager = new TriviaManager(storage);
        Stack<String> commandStack = new Stack<>();
        ArrayList<Task> deletedTask = new ArrayList<>();
        try {
            deadlineCommand.execute(tasks,ui,storage,commandStack,deletedTask,triviaManager);
        } catch (IOException e) {
            assertEquals("An IOException was caught :" + e.getStackTrace()
                    + "The system cannot find the path specified", e.getMessage());
        } catch (ParseException e) {
            assertEquals("Date Time has to be in YYYY-MM-DD HH:mm:ss format",e.getMessage());
        } catch (DukeException e) {
            assertEquals(e.getMessage(),e.getMessage());
        } catch (NullPointerException e) {
            assertEquals(e.getMessage(),e.getMessage());
        }
    }

    @Test
    public void testUpcomingTasks() {
        ArrayList<Task> tasks = new ArrayList<Task>();
        Ui ui = new Ui();
        try {
            ui.UpcomingTask(tasks);
        } catch (ParseException e) {
            assertEquals("Date Time has to be in YYYY-MM-DD HH:mm:ss format",e.getMessage());
        } catch (NullPointerException e) {
            assertEquals(e.getMessage(),e.getMessage());
        }
    }
}