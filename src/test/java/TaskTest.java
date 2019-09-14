import Tasks.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class TaskTest {

    @Test
    public void testgetStatusIcon (){
        assertEquals("[✘]", new Deadline("do assignments", "3/2/2019 1300"));
    }

    @Test
    public void testToString() {
        assertEquals("[✘] do assignments", new ToDos("do assignments").toString());
        assertEquals("[✘] do assignments (by: 3/2/2019 1300)",
                new Deadline("do assignments", "3/2/2019 1300").toString());
        assertEquals("[✘] do assignments (at: 3/2/2019 1300)",
                new Events("do assignments", "3/2/2019 1300").toString());
    }

}
