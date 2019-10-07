import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import task.Deadline;
import task.Task;
import task.TaskList;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class EditCommandTest {
    private LocalDateTime testDate = LocalDateTime.of(2, 2, 2, 2, 2, 2, 2);

    @Test
    public void testEdit() {
        Deadline testTask = new Deadline("Minecraft", testDate);
        ArrayList<Task> test = new ArrayList<>();
        test.add(testTask);
        TaskList testList = new TaskList(test);
        testList.editTaskDescription(0, "Roblox");

        test = testList.getTasks();
        Assertions.assertEquals(test.get(0).description, "Roblox");
    }
}
