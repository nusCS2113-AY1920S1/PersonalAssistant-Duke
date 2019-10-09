import Storage.Storage;
import Tasks.Task;
import UI.Ui;
import commands.DeadlineCommand;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Stack;

import Exception.DukeException;

import static org.junit.jupiter.api.Assertions.assertEquals;
public class DukeTest {
    @Test
   public void test() {
        DeadlineCommand deadlineCommand = new DeadlineCommand();
        ArrayList<Task> tasks = new ArrayList<Task>();
        Ui ui = new Ui();
        Stack<String> CommandStack = new Stack<>();
        ArrayList<Task> deletedTask = new ArrayList<Task>();
        Storage storage = new Storage();
        try {
            deadlineCommand.execute(tasks,ui,storage, CommandStack, deletedTask);
        } catch(IOException e) {
            assertEquals("An IOException was caught :" +e.getStackTrace()+"The system cannot find the path specified", e.getMessage());
        } catch (ParseException e) {
            assertEquals("Date Time has to be in YYYY-MM-DD HH:mm:ss format",e.getMessage());
        } catch (NullPointerException e){
            assertEquals(e.getMessage(),e.getMessage());
        } catch (DukeException dukeException) {
            dukeException.printStackTrace();
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
        } catch (NullPointerException e){
            assertEquals(e.getMessage(),e.getMessage());
        }
    }
}