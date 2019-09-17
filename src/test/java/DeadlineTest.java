import Task.Deadline;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test to see if Deadline can be created
 */

public class DeadlineTest {

    @Test
    public void alwaysTrue () {
        assertEquals(2,2);
    }

    @Test
    public void dummyTest(){
        Deadline deadline = new Deadline("return stuff", false, "2/12/2019 1800");
        assertEquals("return stuff", deadline.getInfo());
        assertEquals("2/12/2019 1800", deadline.getDate());
    }
}
