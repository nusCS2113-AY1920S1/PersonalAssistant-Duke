import CustomExceptions.RoomShareException;
import Enums.TimeUnit;
import Model_Classes.Meeting;
import Operations.CheckAnomaly;
import Operations.Parser;
import Operations.Storage;
import Operations.TaskList;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CheckAnomalyTest {
    private static final Parser parser = new Parser();
    private static final Storage storage = new Storage();
    private static Meeting meeting1, meeting2, meeting3, meeting4, meeting5;
    private static Date at1, at2, at3, at4, at5;
    private static TaskList taskList;

    static {
        try {
            at1 = parser.formatDateCustom_1("12/12/2019 17:00");
            at2 = parser.formatDateCustom_1("12/12/2019 19:00");
            at3 = parser.formatDateCustom_1("12/12/2019 10:00");
            at4 = parser.formatDateCustom_1("12/12/2019 09:00");
            at5 = parser.formatDateCustom_1("21/12/2019 13:00");
            taskList = new TaskList(storage.loadFile("test.txt"));
            meeting1 = new Meeting("test1", at1, 2, TimeUnit.hours);
            meeting2 = new Meeting("test2", at2);
            meeting3 = new Meeting("test3", at3);
            meeting4 = new Meeting("test4", at4, 2, TimeUnit.hours);
            meeting5 = new Meeting("test5", at5);
        } catch (RoomShareException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void durationClashOverlap() { assertEquals(true, new CheckAnomaly().checkTask(meeting1)); }

    @Test
    public void durationClashIntersect() { assertEquals(true, new CheckAnomaly().checkTask(meeting2)); }

    @Test
    public void fixedClashIntersect() { assertEquals(true, new CheckAnomaly().checkTask(meeting3)); }

    @Test
    public void fixedClashOverlap() { assertEquals(true, new CheckAnomaly().checkTask(meeting4)); }

    @Test
    public void noClash() { assertEquals(false, new CheckAnomaly().checkTask(meeting5)); }

}