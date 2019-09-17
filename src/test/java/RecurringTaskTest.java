import duke.DukeException;
import duke.Parser;
import duke.tasks.RecurringTask;
import duke.tasks.Task;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;

public class RecurringTaskTest {
    @Test
    public void simpleTest() {
        //Date date = new Date();
        ArrayList<Task> tasks = new ArrayList<>();
        String input = "todo aaaaaa /daily 1600";
        try {
            System.out.println(Parser.runRecurring(tasks, input, 0, "daily"));
        } catch (DukeException e){
            System.out.println(e.getMessage());
        }
    }
}
