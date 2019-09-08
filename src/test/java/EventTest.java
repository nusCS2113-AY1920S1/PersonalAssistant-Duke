import org.junit.jupiter.api.Test;
import process.DukeException;
import task.Event;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EventTest {
    @Test
    public void dummyTest(){
        assertEquals(2, 2);
    }
    @Test
    public void Test() {
        try {
            new Event("asd 234567 xcvbm !", "12/12/12 1234", false);
            new Event("asfvfs 134", "12/12/12 2300", false);
        } catch (DukeException e) {
            assert false;
        }
        try {
            new Event("asfvfs 134", "12/12/12 9900", false);
            assert false;
        } catch (DukeException e) { }

        try {
            new Event("asfvfs 134", "12/13/0 1200", false);
            assert false;
        } catch (DukeException e) { }

        try {
            new Event("asfvfs 134", "30/2/12 2300", false);
            assert false;
        } catch (DukeException e) { }
    }
}