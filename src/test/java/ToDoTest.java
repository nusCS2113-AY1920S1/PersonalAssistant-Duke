import Task.ToDo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test to see if Todo can be created
 */
public class ToDoTest {

    @Test
    public void alwaysTrue () {
        assertEquals(2,2);
    }

    @Test
    public void dummyTest(){

        ToDo todo = new ToDo("return library books",false);
        assertEquals("return library books", todo.getInfo());
    }
}
