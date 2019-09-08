import org.junit.jupiter.api.Test;
import process.DukeException;
import task.Todo;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TodoTest {
    @Test
    public void dummyTest(){
        assertEquals(2, 2);
    }
    @Test
    public void Test() {
        try {
            new Todo("a a a /by 12/13/12 9934)", false).toString();
            new Todo("a a a /at 12/13/12 9934)", false).toString();
        } catch (DukeException e) {
            assert false;
        }
        try {
            new Todo("", false).toString();
            assert false;
        } catch (DukeException e) {
            assert true;
        }
    }
}