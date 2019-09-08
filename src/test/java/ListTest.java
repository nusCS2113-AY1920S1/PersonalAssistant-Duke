import org.junit.jupiter.api.Test;
import process.DukeException;
import task.TaskList;
import task.Todo;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ListTest {
    @Test
    public void dummyTest(){
        assertEquals(2, 2);
    }
    @Test
    public void Test() {
        try {
            TaskList tasks = new TaskList();
            tasks.add(new Todo("abcdef", false));
            tasks.add(new Todo("zxcv", false));
            if (tasks.print_list().indexOf("Here are the tasks in your list:") > -1)
                assert true;
            else assert false;
        } catch (DukeException e) {
            assert false;
        } catch (Exception e) {
            assert false;
        }
        try {
            TaskList tasksEmpty = new TaskList();
            tasksEmpty.print_list();
            assert false;
        } catch (DukeException e) {
            assert true;
        } catch (Exception e) {
            assert false;
        }
    }
}