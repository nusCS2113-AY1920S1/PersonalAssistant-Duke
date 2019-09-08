import org.junit.jupiter.api.Test;
import process.DukeException;
import task.Deadline;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeadlineTest {
    @Test
    public void dummyTest(){
        assertEquals(2, 2);
    }
    @Test
    public void Test() {
        try {
            new Deadline("asd 234567 xcvbm !", "12/12/12 1234", false);
            new Deadline("asfvfs 134", "12/12/12 2300", false);
        } catch (DukeException e) {
            assert false;
        }
        try {
            new Deadline("asfvfs 134", "12/12/12 9900", false);
            assert false;
        } catch (DukeException e) { }

        try {
            new Deadline("asfvfs 134", "12/13/0 1200", false);
            assert false;
        } catch (DukeException e) { }

        try {
            new Deadline("asfvfs 134", "30/2/12 2300", false);
            assert false;
        } catch (DukeException e) { }
        assert true;
    }
}