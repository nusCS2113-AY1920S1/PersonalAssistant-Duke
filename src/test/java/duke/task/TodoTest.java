package duke.task;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class TodoTest {
    Todo todo = new Todo("This is a test");

    @Test
    void testToString1() {
        assertEquals(todo.toString(), "[T][\u2718] This is a test\n" );
    }

    @Test
    void testGetSymbol() {
        assertEquals(todo.getSymbol(), "[T]");
    }

    @Test
    void testWriteToFile() {
        assertEquals(todo.writeToFile(), "T | 0 | This is a test");
    }
}