import org.junit.jupiter.api.Test;
import process.DukeException;
import task.TaskList;
import task.Todo;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DoneTest {
    @Test
    public void dummyTest(){
        assertEquals(2, 2);
    }
    @Test
    public void Test() {
        try {
            TaskList tasks = new TaskList();
            tasks.add(new Todo("asd",false));
            tasks.doneTask(0);
            if (tasks.get(0).toString().indexOf("Done") > -1)
                assert true;
            else assert false;
        } catch (DukeException e) {
            assert false;
        } catch (Exception e) {
            assert false;
        }
    }
}