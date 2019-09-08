import org.junit.jupiter.api.Test;
import process.DukeException;
import task.TaskList;
import task.Todo;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeleteTest {
    @Test
    public void dummyTest(){
        assertEquals(2, 2);
    }
    @Test
    public void Test() {
        try {
            TaskList tasks = new TaskList();
            tasks.add(new Todo("x",false));
            tasks.deleteTask(0);
            if (tasks.size() == 0)
                assert true;
            else assert false;
        } catch (DukeException e) {
            assert false;
        } catch (Exception e) {
            assert false;
        }
    }
}
