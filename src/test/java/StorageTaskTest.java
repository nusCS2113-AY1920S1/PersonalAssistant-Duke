import duke.exception.DukeException;
import storage.task.Task;
import storage.task.TaskList;
import storage.task.TaskType;
import storage.StorageTask;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StorageTaskTest {

    @Test
    void loadData() {
        StorageTask storagetask = new StorageTask("testDataLoad.txt");
        TaskList taskListResult = new TaskList();
        try {
            storagetask.loadData(taskListResult);
        } catch (DukeException e) {
            System.out.println(e.getMessage());
        }
        final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HHmm");
        //TODO yoda things/ tmrw at last@|@false
        Task taskResult = taskListResult.get(0);
        assertEquals(TaskType.TODO, taskResult.getTaskType(),
                "Loaded wrong taskType");
        assertEquals("yoda things", taskResult.getTaskName(),
                "Loaded wrong taskName");
        assertEquals("", taskResult.getDetailDesc(),
                "Loaded wrong detailDesc");
        assertEquals("tmrw at last", taskResult.getTaskDetails(),
                "Loaded wrong taskDetails");
        assertEquals(false, taskResult.getIsDone(),
                "Loaded wrong isDone");

        //EVENT something/by somewhen@|@true
        taskResult = taskListResult.get(1);
        assertEquals(TaskType.EVENT, taskResult.getTaskType(),
                "Loaded wrong taskType");
        assertEquals("something", taskResult.getTaskName(),
                "Loaded wrong taskName");
        assertEquals("by", taskResult.getDetailDesc(),
                "Loaded wrong detailDesc");
        assertEquals("somewhen", taskResult.getTaskDetails(),
                "Loaded wrong taskDetails");
        assertEquals(true, taskResult.getIsDone(),
                "Loaded wrong isDone");

        //DEADLINE this/by 19/09/19 1015@|@false
        taskResult = taskListResult.get(2);
        assertEquals(TaskType.DEADLINE, taskResult.getTaskType(),
                "Loaded wrong taskType");
        assertEquals("this", taskResult.getTaskName(),
                "Loaded wrong taskName");
        assertEquals("by", taskResult.getDetailDesc(),
                "Loaded wrong detailDesc");
        assertEquals("19/09/19 1015", taskResult.getTaskDetails(),
                "Loaded wrong taskDetails");
        assertEquals(false, taskResult.getIsDone(),
                "Loaded wrong isDone");
        assertEquals("19/09/19", taskResult.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yy")),
                "Loaded wrong taskDetails");
        assertEquals("1015", taskResult.getTime().format(DateTimeFormatter.ofPattern("HHmm")));

    }

    @Test
    void saveData() {
        // testDataLoad is the test Data
        // Follow the Storage Format when inputting new test cases
        StorageTask storageExpected = new StorageTask("testDataLoad.txt");
        StorageTask storageSaved = new StorageTask("testDataSave.txt");
        TaskList taskList = new TaskList();
        try {
            storageExpected.loadData(taskList);
        } catch (DukeException e) {
            System.out.println(e.getMessage());
        }
        try {
            storageSaved.saveData(taskList);
        } catch (DukeException e) {
            System.out.println(e.getMessage());
        }
        File fileExpected = new File("testDataLoad.txt");
        File fileSaved = new File("testDataSave.txt");
        try {
            Scanner scannerExpected = new Scanner(fileExpected);
            Scanner scannerSaved = new Scanner(fileSaved);
            while (scannerExpected.hasNextLine()) {
                assertEquals(scannerExpected.nextLine(), scannerSaved.nextLine());
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}
