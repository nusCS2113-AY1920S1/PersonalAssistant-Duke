package seedu.hustler.task;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RecurringDeadlineTest {

    //Create variables for sampleTask
    private LocalDateTime ldt1 = LocalDateTime.of(2019,11,20,23,59);
    private LocalDateTime ldt2 = LocalDateTime.of(2019,11,27,23,59);
    private LocalDateTime now = LocalDateTime.of(2019,11,13,23,59);
    private int oneWeekInMinutes = 7 * 24 * 60;

    private RecurringDeadline sampleTask1 = new RecurringDeadline("homework", ldt1, "M",
            "geog", now, "1 weeks", oneWeekInMinutes,false);
    private RecurringDeadline sampleTask2 = new RecurringDeadline("homework", ldt2, "M",
            "geog", now, "1 weeks", oneWeekInMinutes,false);

    @Test
    public void test_constructor() {
        assertEquals(sampleTask1, new RecurringDeadline("homework", ldt1, "M",
                "geog", now, "1 weeks", oneWeekInMinutes,false));
    }

    @Test
    public void test_toString_conversion() {
        assertEquals("[D][-][MED][#geog] homework (by: 20th of November 2019, 11:59PM)"
                + " (Repeats every 1 weeks)", sampleTask1.toString());
    }

    @Test
    public void test_toSaveFormat_conversion() {
        assertEquals("D|0|M|geog|homework|20/11/2019 2359|2019-11-13T23:59|1 weeks|10080|false",
                sampleTask1.toSaveFormat());
    }

    @Test
    public void test_addNextRecurrence() {
        TaskList sampleTaskList = new TaskList(new ArrayList<>());
        sampleTask1.addNextRecurrence(sampleTaskList);
        // sampleTask2 is the next recurrence for sampleTask1
        // check if addNextRecurrence works correctly (i.e. automatically adds next cycle of sampleTask1)
        assertEquals(sampleTask2,sampleTaskList.get(0));
    }
}
