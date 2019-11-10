import CustomExceptions.RoomShareException;
import Model_Classes.Assignment;
import Operations.*;
import Operations.subTaskCreator;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class subTaskCreatorTest {
    private static final Parser parser = new Parser();
    private static final Storage storage = new Storage();
    private static TaskList taskList;
    private static Date by;
    private static Assignment assignment;

    static {
        try {
            taskList = new TaskList(storage.loadFile("test.txt"));
            by = parser.formatDateDDMMYY("12/12/2019 17:00");
            assignment = new Assignment("test", by);
            taskList.add(assignment);
            new subTaskCreator(3, "one,two");
        } catch (RoomShareException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSubtask() {
        String one = ((Assignment) TaskList.currentList().get(3)).getSubTasks().get(0);
        String two = ((Assignment) TaskList.currentList().get(3)).getSubTasks().get(1);
        assertEquals(one, "one");
        assertEquals(two, "two");
    }
}