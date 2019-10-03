import org.testng.annotations.Test;

import java.io.File;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DukeTest {
    @Test
    public void testResponse() {
        File newFile = new File("data/tasks.txt");
        newFile.delete();

        Duke newDuke = new Duke();
        assertEquals("You have no tasks in your list", newDuke.getResponse("list"));
        assertEquals("Got it. I've added this task: \n  [T][NOT DONE] Hello World\nNow you have 1 task(s) in the list.",
                newDuke.getResponse("todo Hello World"));
    }
}