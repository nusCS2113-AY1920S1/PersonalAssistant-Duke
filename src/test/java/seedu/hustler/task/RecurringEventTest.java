package seedu.hustler.task;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RecurringEventTest {

    //Create variables for sampleTask
    private LocalDateTime ldt1 = LocalDateTime.of(2019,11,20,18,0);
    private LocalDateTime ldt2 = LocalDateTime.of(2019,11,27,18,0);
    private LocalDateTime now = LocalDateTime.of(2019,11,13,23,59);
    private int oneWeekInMinutes = 7 * 24 * 60;

    private RecurringEvent sampleTask1 = new RecurringEvent("family dinner", ldt1, "M",
            "food", now, "1 weeks", oneWeekInMinutes,false);
    private RecurringEvent sampleTask2 = new RecurringEvent("family dinner", ldt2, "M",
            "food", now, "1 weeks", oneWeekInMinutes,false);

    @Test
    public void test_constructor() {
        assertEquals(sampleTask1, new RecurringEvent("family dinner", ldt1, "M",
                "food", now, "1 weeks", oneWeekInMinutes,false));
    }

    @Test
    public void test_toString_conversion() {
        assertEquals("[E][-][MED][#food] family dinner (at: 20th of November 2019, 6:00PM) (Repeats every 1 weeks)",
                sampleTask1.toString());
    }

    @Test
    public void test_toSaveFormat_conversion() {
        assertEquals("E|0|M|food|family dinner|20/11/2019 1800|2019-11-13T23:59|1 weeks|10080|false",
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
