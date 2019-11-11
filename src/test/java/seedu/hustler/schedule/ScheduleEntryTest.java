package seedu.hustler.schedule;

import org.junit.jupiter.api.Test;
import seedu.hustler.schedule.time.TimeRemainingStub;
import static org.junit.jupiter.api.Assertions.assertEquals;
import seedu.hustler.task.ToDo;

public class ScheduleEntryTest {
    @Test
    /**
     * Testing updateTimeSpent method.
     */
    void updateTimeSpent() {
        ToDo todo = new ToDo("random todo");
        ScheduleEntry entry = new ScheduleEntry(todo, 0);
        entry.updateTimeSpent(100);
        assertEquals(100, entry.getTimeSpent());
    }
    
    @Test
    /**
     * Testing setTimeAlloc.
     */
    void setTimeAlloc() {
        ToDo todo = new ToDo("random todo");
        ScheduleEntry entry = new ScheduleEntry(todo, 0);
        entry.setTimeAlloc(100);
        assertEquals(100, entry.getTimeAlloc());
    }

    @Test
    /**
     * Testing getPriorityScore.
     */
    void getPriorityScore() {
        ToDo todo = new ToDo("random todo");
        ScheduleEntry entry = new ScheduleEntry(todo, 1000, new TimeRemainingStub());
        Priority priority = new Priority(new TimeRemainingStub());
        assertEquals(priority.getPriorityScore(todo, 1000), entry.getPriorityScore());
    }
}
