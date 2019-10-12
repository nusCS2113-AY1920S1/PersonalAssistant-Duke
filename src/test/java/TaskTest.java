import controllers.TaskFactory;
import org.junit.jupiter.api.Test;
import views.CLIView;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskTest {
    private TaskFactory consoleInputFactory = new TaskFactory();

    @Test
    public void alwaysTrue() {
        assertEquals(2, 2);
    }

    public void testAddTask(){
    }
}
