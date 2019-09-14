import Duke.DukeException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DukeTest {
    @Test
    public void Test() throws DukeException {
        dummyTest();
        //Tests for todo
        new TodoTest().test("todo test");
        new TodoTest().jog("todo jog");
        new TodoTest().todo("todo todo");

        //Test for deadline
        new DeadlineTest().test("deadline test /by 0000");
        new DeadlineTest().examBy_Date("deadline exam /by 01/01/2019");

        //Test for event
        new EventTest().test("event test /at 0000");
        new EventTest().birthdayAt_myBday("event bday /at 06/06/2019");
    }

    public void dummyTest() {
        assertEquals(2,2);
    }

}
