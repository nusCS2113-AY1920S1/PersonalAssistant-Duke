package modeltests;

import org.junit.jupiter.api.Test;
import duke.models.ToDo;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ToDoTest {
    String name = "Swimming";
    String date = "May 10, 2019";
    String loc = "NUS";
    String start = "0800";
    String end = "0900";
    ToDo toDo = new ToDo(
        "0800",
        "0900",
        "NUS",
        "Swimming",
        "May 10, 2019");
    @Test
    void alwaysTrue() {
        assertEquals(2, 2);
    }

    @Test
    void testName() {
        assertEquals(name, toDo.getClassName());
    }

    @Test
    void testDate() {
        assertEquals(date, toDo.getDate());
    }

    @Test
    void testLoc() {
        assertEquals(loc, toDo.getLocation());
    }

    @Test
    void testStart() {
        assertEquals(start, toDo.getStartTime());
    }

    @Test
    void testEnd () {
        assertEquals(end, toDo.getEndTime());
    }


}
