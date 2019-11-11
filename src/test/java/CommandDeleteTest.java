import executor.command.CommandDelete;
import storage.task.ToDo;
import org.junit.jupiter.api.Test;
import storage.StorageManager;


import static org.junit.jupiter.api.Assertions.assertEquals;



public class CommandDeleteTest {
    @Test
    void execute() {
        StorageManager storageManager = new StorageManager();

        storageManager.getTaskList().addTask(new ToDo("assignment"));

        CommandDelete d1 = new CommandDelete("delete 1");
        d1.execute(storageManager);
        String result = d1.getInfoCapsule().getOutputStr();
        assertEquals("Task 1)assignment has been deleted.\n", result);

        CommandDelete d2 = new CommandDelete("delete 34f5t");
        d2.execute(storageManager);
        String output = d2.getInfoCapsule().getOutputStr();
        assertEquals("Invalid index input. Please enter an integer", output);

        CommandDelete d3 = new CommandDelete("delete 3");
        d3.execute(storageManager);
        String result1 = d3.getInfoCapsule().getOutputStr();
        assertEquals("Unable to get Task Name of Task at index 2\n", result1);

    }
}



