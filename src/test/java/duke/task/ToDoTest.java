package duke.task;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ToDoTest {
    @Test
    public void testToString() {
        ToDo newToDo = new ToDo("Hello World");
        assertEquals("[T][NOT DONE] Hello World", newToDo.toString());

        newToDo.markAsDone();
        assertEquals("[T][DONE] Hello World", newToDo.toString());

    }
}