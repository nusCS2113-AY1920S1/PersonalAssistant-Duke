package sgtravel.model.planning;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TodoTest {

    @Test
    void testToString() {
        Todo todo = new Todo("Homework");
        assertEquals(todo.toString(), "Homework");
    }
}
