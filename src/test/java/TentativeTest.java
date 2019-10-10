import duke.DateTime;
import duke.items.tasks.Tentative;
import duke.items.tasks.Task;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class TentativeTest {

    @org.junit.jupiter.api.Test
    void testToString_createAndMarkDoneNewTask() {
        DateTime startDate = new DateTime("09/19/2019 12:00");
        DateTime endDate = new DateTime("09/27/2019 12:00");
        Task task = new Tentative("holidays", startDate, endDate);
        assertEquals("[TE][✗] holidays (around: 09/19/2019 12:00 to 09/27/2019 12:00 - date not fixed)",
                task.toString());
        assertNotEquals("[TE][✓] holidays (around: 09/19/2019 12:00 to 09/27/2019 12:00 - date not fixed)",
                task.toString());
        task.markDone();
        assertEquals("[TE][✓] holidays (around: 09/19/2019 12:00 to 09/27/2019 12:00 - date not fixed)",
                task.toString());
        assertNotEquals("[TE][✗] holidays (around: 09/19/2019 12:00 to 09/27/2019 12:00 - date not fixed)",
                task.toString());
    }

    @org.junit.jupiter.api.Test
    void testStoreString_createAndMarkDoneNewTask() {
        DateTime startDate = new DateTime("09/19/2019 12:00");
        DateTime endDate = new DateTime("09/27/2019 12:00");
        Task task = new Tentative("holidays", startDate, endDate);
        assertEquals("TE | 0 | holidays | 09/19/2019 12:00 | 09/27/2019 12:00",
                task.storeString());
        assertNotEquals("TE | 1 | holidays | 09/19/2019 12:00 | 09/27/2019 12:00",
                task.storeString());
        task.markDone();
        assertEquals("TE | 1 | holidays | 09/19/2019 12:00 | 09/27/2019 12:00",
                task.storeString());
        assertNotEquals("TE | 0 | holidays | 09/19/2019 12:00 | 09/27/2019 12:00",
                task.storeString());
    }
}