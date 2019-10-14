import spinbox.items.tasks.Fixed;
import spinbox.items.tasks.Task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class FixedTest {

    @org.junit.jupiter.api.Test
    void testToString_createAndMarkDoneNewTask_newFixedDurationTaskCreatedAndMarkedDoneWithToStringFormat() {
        Task task = new Fixed("read sales report", "2 hours");
        assertEquals("[F][✗] read sales report (needs: 2 hours)", task.toString());
        assertNotEquals("[F][✓] read sales report (needs: 2 hours)", task.toString());
        task.markDone();
        assertEquals("[F][✓] read sales report (needs: 2 hours)", task.toString());
        assertNotEquals("[F][✗] read sales report (needs: 2 hours)", task.toString());
    }

    @org.junit.jupiter.api.Test
    void testStoreString_createAndMarkDoneNewTask_newFixedDurationTaskCreatedAndMarkedDoneWithStoreStringFormat() {
        Task task = new Fixed("read sales report", "2 hours");
        assertEquals("F | 0 | read sales report | 2 hours", task.storeString());
        assertNotEquals("F | 1 | read sales report | 2 hours", task.storeString());
        task.markDone();
        assertEquals("F | 1 | read sales report | 2 hours", task.storeString());
        assertNotEquals("F | 0 | read sales report | 2 hours", task.storeString());
    }
}