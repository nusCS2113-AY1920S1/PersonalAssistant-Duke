import CustomExceptions.RoomShareException;
import Model_Classes.Event;
import Model_Classes.FixedDuration;
import Operations.CheckAnomaly;
import Operations.Storage;
import Operations.TaskList;
import Operations.Parser;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Date;

public class CheckAnomalyTest {
    private static final Parser parser = new Parser();
    private static final Storage storage = new Storage();
    private static FixedDuration currEvent;
    private static Date at1, at2, at3, at4, at5;
    private static TaskList taskList;

    static {
        try {
            at1 = parser.formatDate("12/12/2019 12:00");
            at2 = parser.formatDate("20/12/2019 12:00");
            at3 = parser.formatDate("12/12/2019 10:00");
            at4 = parser.formatDate("20/12/2019 13:00");
            at5 = parser.formatDate("21/12/2019 13:00");
            taskList = new TaskList(storage.loadFile("data.txt"));
            FixedDuration fixedDuration = new FixedDuration("test1", at1, 2);
            Event event = new Event("test2", at2);
        } catch (RoomShareException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void timeCheckFixedTest() { assertEquals(true, new CheckAnomaly().checkTime(new FixedDuration("check", at3, 3))); }

    @Test
    public void timeCheckOnlyDateTrueTest() {
        assertEquals(true, new CheckAnomaly().checkTime(at1));
    }

    @Test
    public void timeCheckOnlyDateFalseTest() {
        assertEquals(false, new CheckAnomaly().checkTime(at5));
    }

}