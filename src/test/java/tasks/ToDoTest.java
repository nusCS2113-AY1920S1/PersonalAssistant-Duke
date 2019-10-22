package tasks;

import model.tasks.ToDo;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ToDoTest {
    @Test
    public void checkDescription() {
        String description = "This is a test todo";
        ToDo temp = new ToDo(description);
        assertEquals(description, temp.getDescription());
    }


}
