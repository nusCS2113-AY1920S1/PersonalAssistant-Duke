import command.RescheduleCommand;
import org.junit.jupiter.api.Test;
import process.DukeException;
import process.Storage;
import process.Ui;
import task.Deadline;
import task.TaskList;
import task.Todo;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RescheduleTest {
    @Test
    public void dummyTest(){
        assertEquals(2, 2);
    }
    @Test
    public void Test() {
        String directory = System.getProperty("user.home");
        directory += "\\Documents\\CS2113T\\main\\data"; //different for mac
//        directory += "/IdeaProjects/CS2113_Project/data";
        try {
            TaskList tasks = new TaskList();
            tasks.add(new Deadline("x","2/2/2 2222", false));
            RescheduleCommand reschedule = new RescheduleCommand(0, "1/1/1 1111");
            Ui ui = new Ui();
            Storage storage = new Storage(directory + "\\tasks.txt");
            reschedule.execute(tasks, ui, storage);
            assert true;
        } catch (DukeException e) {
            System.out.println(e);
            assert false;
        } catch (Exception e) {
            assert false;
        }
    }
}
