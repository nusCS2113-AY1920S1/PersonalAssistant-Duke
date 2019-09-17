package duke.calendarMap;

import duke.task.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DateMapTest {
    DateMap actual = new DateMap();
    DateMap expected = new DateMap();

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
    void testAddTask() {
        actual.addTask("2/3/1997 1", new Event("test", "1"));
        actual.addTask("2/3/1997 2", new Event("test2", "2"));
        actual.addTask("2/3/1997 3", new Event("test3", "3"));
        actual.addTask("2/3/1997 4", new Event("test4", "4"));

        LocalDate key = LocalDate.of(1997, 3, 2);


        TimeMap timeMap = actual.get(key);

        assertEquals(4, timeMap.size());
        assertEquals(expected.get(key).get("1"), actual.get(key).get("1"));
        assertEquals(expected.get(key).get("2"), actual.get(key).get("2"));
        assertEquals(expected.get(key).get("3"), actual.get(key).get("3"));
        assertEquals(expected.get(key).get("4"), actual.get(key).get("4"));
    }

    @Test
    void testDeleteTask() {
        TimeMap timeMap = new TimeMap();
        timeMap.put("1", new Event("test", "1"));
        timeMap.put("2", new Event("test2", "2"));
        timeMap.put("3", new Event("test3", "3"));
        timeMap.put("4", new Event("test4", "4"));
        timeMap.put("5", new Event("test5", "5"));

        LocalDate key = LocalDate.of(1997, 3, 2);

        actual.put(key, timeMap);
        actual.deleteTask("2/3/1997 5", new Event("test5", "5"));

        assertEquals(4, timeMap.size());
        assertEquals(expected.get(key).get("1"), actual.get(key).get("1"));
        assertEquals(expected.get(key).get("2"), actual.get(key).get("2"));
        assertEquals(expected.get(key).get("3"), actual.get(key).get("3"));
        assertEquals(expected.get(key).get("4"), actual.get(key).get("4"));
    }
//
//    @Test
//    void testViewSchedule() {
//    }
}