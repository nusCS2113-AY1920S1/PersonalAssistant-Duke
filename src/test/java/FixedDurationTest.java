import CustomExceptions.RoomShareException;
import Model_Classes.FixedDuration;
import Operations.Parser;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Date;

public class FixedDurationTest {
    private static final Parser parser = new Parser();
    private static int duration = 2;
    private static String user = "general";
    private static String description = "washing machine";
    private static Date at;
    private static String unit = "hours";

    static {
        try {
            at = parser.formatDate("22/12/2019 18:00");
        } catch (RoomShareException e) {
            e.printStackTrace();
        }
    }

    private static FixedDuration fd = new FixedDuration(description,at,duration,user, unit);

    @Test
    public void getDuration() {
        assertEquals(2, duration);
    }

    @Test
    public void testToString(){
        assertEquals("[E][\u2718] washing machine (general) (on: Sun Dec 22 18:00:00 SGT 2019) (done in: 2 hours)", fd.toString());
    }
}