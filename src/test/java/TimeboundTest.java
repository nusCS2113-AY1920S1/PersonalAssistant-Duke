import gazeeebo.Tasks.Timebound;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TimeboundTest {
    private Timebound timeboundTest = new Timebound("task to be done", "2019-08-12 and 2019-08-13");

    @Test
    void getDescriptionTest() {

        assertEquals("[P][ND] task to be done(between: 12 Aug 2019 and 13 Aug 2019)", timeboundTest.listFormat());
    }

    @Test
    void getFormatTest() {
        assertEquals("P|ND|task to be done|2019-08-12 and 2019-08-13", timeboundTest.toString());
    }
}