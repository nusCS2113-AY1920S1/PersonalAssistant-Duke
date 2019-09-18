package duke.calendarMap;

import duke.exception.DukeTaskClashException;
import duke.task.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DateMapTest {
    private DateMap actual = new DateMap();
    private DateMap expected = new DateMap();

    @BeforeEach
    void initialize() {
        TimeMap timeMap = new TimeMap();
        timeMap.put("1", new Event("test", "1"));
        timeMap.put("2", new Event("test2", "2"));
        timeMap.put("3", new Event("test3", "3"));
        timeMap.put("4", new Event("test4", "4"));

        LocalDate key = LocalDate.of(1997, 3, 2);

        expected.put(key, timeMap);
    }

    @Test
    void testAddTask() throws DukeTaskClashException {
        actual.addTask("2/3/1997 1", new Event("test", "1"));
        actual.addTask("2/3/1997 2", new Event("test2", "2"));
        actual.addTask("2/3/1997 3", new Event("test3", "3"));
        actual.addTask("2/3/1997 4", new Event("test4", "4"));

        LocalDate key = LocalDate.of(1997, 3, 2);


        TimeMap timeMap = actual.get(key);

        assertEquals(4, timeMap.size());
        assertTrue(expected.keySet().containsAll(actual.keySet()));
    }

    @Test
    void testDeleteTask() {
        TimeMap timeMap = new TimeMap();
        timeMap.put("1", new Event("test", "1"));
        timeMap.put("2", new Event("test2", "2"));
        timeMap.put("3", new Event("test3", "3"));
        timeMap.put("4", new Event("test4", "4"));
        Event event = new Event("test5", "5");
        timeMap.put("5", event);

        LocalDate key = LocalDate.of(1997, 3, 2);

        actual.put(key, timeMap);
        actual.deleteTask("2/3/1997 5", event);

        assertEquals(4, timeMap.size());
        assertTrue(expected.keySet().containsAll(actual.keySet()));
    }

    @Test
    void testViewSchedule() {
        Event event1 = new Event("test", "1");
        Event event2 = new Event("test2", "2");
        Event event3 = new Event("test3", "3");
        Event event4 = new Event("test4", "4");

        String expected = "Here are your tasks for 2/3/1997: \n"
                + "1. " + event1.toString()
                + "2. " + event2.toString()
                + "3. " + event3.toString()
                + "4. " + event4.toString();

        assertEquals(expected, this.expected.viewSchedule("2/3/1997"));
        expected = "☹ OOPS!!! There isn't any task for this date.\n"
                + "Please try again. \n";
        assertEquals(expected, this.actual.viewSchedule("2/3/1997"));
    }
}