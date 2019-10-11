import Tasks.Deadline;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeadlineTest {
    @Test
    public void stringConversionTest() {
        assertEquals("[D][\u2718] Test Deadline (by: Mon 02/12/2019 06:00 PM)", new Deadline("Test Deadline", "Mon 02/12/2019 06:00 PM").toString());
    }
}
