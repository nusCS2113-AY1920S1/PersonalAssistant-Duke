import org.junit.jupiter.api.Test;
import task.Deadline;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeadlineTest {

    private final Deadline D_TEST = new Deadline("finish JUnit testing", "23rd of January 2019, 3pm");


    @Test
    void testToString() {
        assertEquals(D_TEST.toString(), "[D][✘] finish JUnit testing (by: 23rd of January 2019, 3pm)");
    }

//    @Test
//    void testToData() {
//
//    }
}
