import executor.task.Task;
import executor.task.TaskList;
import executor.task.TaskType;
import storage.StorageTask;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StorageTaskTest {

    @Test
    void loadData() {
        StorageTask storagetask = new StorageTask("testDataLoad.txt");
        TaskList taskListResult = storagetask.loadData();
        final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HHmm");
        //TODO yoda things/ tmrw at last@|@false
        Task taskResult = taskListResult.getList().get(0);
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
        taskResult = taskListResult.getList().get(1);
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

        //DEADLINE this/by 19/09/2019 1015@|@false
        taskResult = taskListResult.getList().get(2);
        assertEquals(TaskType.DEADLINE, taskResult.getTaskType(),
                "Loaded wrong taskType");
        assertEquals("this", taskResult.getTaskName(),
                "Loaded wrong taskName");
        assertEquals("by", taskResult.getDetailDesc(),
                "Loaded wrong detailDesc");
        assertEquals("19/09/2019 1015", taskResult.getTaskDetails(),
                "Loaded wrong taskDetails");
        assertEquals(false, taskResult.getIsDone(),
                "Loaded wrong isDone");
        assertEquals("19/09/2019 1015", dateFormat.format(taskResult.getDatetime()),
                "Loaded wrong taskDetails");

    }

    @Test
    void saveData() {
        // testDataLoad is the test Data
        // Follow the Storage Format when inputting new test cases
        StorageTask storageExpected = new StorageTask("testDataLoad.txt");
        StorageTask storageSaved = new StorageTask("testDataSave.txt");
        TaskList taskList = storageExpected.loadData();

        storageSaved.saveData(taskList);
        File fileExpected = new File("testDataLoad.txt");
        File fileSaved = new File("testDataSave.txt");
        try {
            Scanner scannerExpected = new Scanner(fileExpected);
            Scanner scannerSaved = new Scanner(fileSaved);
            while (scannerExpected.hasNextLine()) {
                assertEquals(scannerExpected.nextLine(), scannerSaved.nextLine());
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
