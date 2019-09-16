import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import task.*;
/**
 * This class implements the unit testing code for the To-do class.
 *
 * @author Sai Ganesh Suresh
 * @version v1.0
 */
public class TodoTest {

    Todo todo = new Todo("testing todo");

    @Test
    public void testTodoCreation(){
        String title = todo.description;
        Assertions.assertEquals(title, "testing todo");
        Assertions.assertEquals(todo.toString(), "[T][" + "\u2718" + "] testing todo");
    }
}