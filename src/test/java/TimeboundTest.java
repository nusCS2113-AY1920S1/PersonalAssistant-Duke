import Tasks.Timebound;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TimeboundTest {
    private Timebound timeboundTest = new Timebound("task to be done", "2019-08-12 and 2019-08-13");

    @Test
    void getDescriptionTest() {
            assertEquals ("[P][\u2718] task to be done(between: 2019-08-12 and 2019-08-13)", timeboundTest.listFormat());
        }

    @Test
    void getFormatTest() {
            assertEquals("P | \u2718 | task to be done | between: 2019-08-12 and 2019-08-13", timeboundTest.toString());
        }

    }

