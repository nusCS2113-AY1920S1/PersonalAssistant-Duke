import org.junit.jupiter.api.Test;
import process.DukeException;
import task.TaskList;
import task.Todo;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FindTest {
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
            if (tasks.find("abcdef").indexOf("abcdef") > -1 && tasks.find("abcdef").indexOf("zxcv") == -1 && tasks.find("abczxc").indexOf("Sorry") > -1)
                assert true;
            else assert false;
        } catch (DukeException e) {
            assert false;
        } catch (Exception e) {
            assert false;
        }
    }
}