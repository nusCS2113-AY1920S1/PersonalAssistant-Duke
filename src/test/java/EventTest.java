import CustomExceptions.RoomShareException;
import Model_Classes.Assignment;
import Operations.Parser;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Date;

public class EventTest {
    private static final Parser parser = new Parser();
    private static Date by;

    static {
        try {
            by = parser.formatDate("22/12/2019 18:00");
        } catch (RoomShareException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testStringConversion() {
        assertEquals("[D][\u2718] homework (by: Sun Dec 22 18:00:00 SGT 2019)", new Assignment("homework", by).toString());
    }
}