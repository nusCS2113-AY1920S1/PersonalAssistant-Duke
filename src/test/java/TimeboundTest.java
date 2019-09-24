import Tasks.Timebound;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TimeboundTest {
    private Timebound timeboundTest = new Timebound("task to be done", "Jan 15th and Jan 25th");

    @Test
    void getDescriptionTest() {
            assertEquals ("[P][\u2718] task to be done (between: Jan 15th and Jan 25th)", timeboundTest.listFormat());
        }

    @Test
    void getFormatTest() {
            assertEquals("P | \u2718 | task to be done | Jan 15th and Jan 25th", timeboundTest.toString());
        }

    }

