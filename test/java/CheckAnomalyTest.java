package java;

import CustomExceptions.DukeException;
import Model_Classes.Deadline;
import Model_Classes.Event;
import Model_Classes.FixedDuration;
import Operations.CheckAnomaly;
import Operations.TaskList;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Date;

public class CheckAnomalyTest {
    private static FixedDurration currEvent;
    private static Date at1, at2, at3, at4, at5;
    private static TaskList taskList;

    static {
        try {
            at1 = parser.formatDate("12/12/2019 12:00");
            at2 = parser.formatDate("20/12/2019 12:00");
            at3 = parser.formatDate("12/12/2019 10:00");
            at4 = parser.formatDate("20/12/2019 13:00");
            at4 = parser.formatDate("21/12/2019 13:00");
        } catch (DukeException e) {
            e.printStackTrace();
        }
    }

    @Before
    public void inputList() {
        taskList = new TaskList(storage.loadFile("data.txt"));
        FixedDuration fixedDuration = new FixedDuration("test1", at1, 2);
        Event event = new Event("test2", at2);
    }

    @Test
    public void timeCheckFixedTest() {
        assertEquals(true, new CheckAnomaly().checkTime(new FixedDuration("check", at3, 3)));
    }

    @Test
    public void timeCheckFalseEventTest() {
        assertEquals(false, new CheckAnomaly().checkTime(new Event("check", at3)));
    }

    @Test
    public void timeCheckEventTrueTest() {
        assertEquals(true, new CheckAnomaly().checkTime(new Event("check", at4)));
    }

    @Test
    public void timeCheckOnlyDateTrueTest() {
        assertEquals(true, new CheckAnomaly().checkTime(at1));
    }

    @Test
    public void timeCheckOnlyDateFalseTest() {
        assertEquals(false, new CheckAnomaly().checkTime(at5));
    }

}