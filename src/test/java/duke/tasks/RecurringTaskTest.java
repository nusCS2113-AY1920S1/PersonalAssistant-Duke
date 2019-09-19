package duke.tasks;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Suther David Samuel (A0182488N)
 * JUnit testing for RecurringTask
 */

public class RecurringTaskTest {

    @Test
    public void toString_testingToString_success(){
        assertEquals("[R][x] eatmacs (daily on: 19/12/2012 at evenings)", new RecurringTask("eatmacs", "19/12/2012", "evenings", "daily").toString());
    }

    @Test
    public void isDateValid_invalidDate_false(){
        assertEquals(false, RecurringTask.isDateVaid(99,99,1999));
    }
}
