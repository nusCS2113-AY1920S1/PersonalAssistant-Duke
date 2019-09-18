import duke.command.CommandNewTask;
import duke.task.Task;
import duke.task.TaskList;
import duke.task.TaskType;
import duke.worker.Parser;
import duke.worker.Storage;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StorageTest {

    @Test
    void loadData() {
        Storage storage = new Storage("testDataLoad.txt");
        TaskList taskListResult = storage.loadData();
        final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy");
        //TODO yoda things/ tmrw at last@|@false
        Task taskResult = taskListResult.getList().get(0);
        assertEquals(TaskType.TODO, taskResult.taskType,
                "Loaded wrong taskType");
        assertEquals("yoda things", taskResult.taskName,
                "Loaded wrong taskName");
        assertEquals("", taskResult.detailDesc,
                "Loaded wrong detailDesc");
        assertEquals("tmrw at last", taskResult.taskDetails,
                "Loaded wrong taskDetails");
        assertEquals(false, taskResult.isDone,
                "Loaded wrong isDone");

        //EVENT something/by somewhen@|@true
        taskResult = taskListResult.getList().get(1);
        assertEquals(TaskType.EVENT, taskResult.taskType,
                "Loaded wrong taskType");
        assertEquals("something", taskResult.taskName,
                "Loaded wrong taskName");
        assertEquals("by", taskResult.detailDesc,
                "Loaded wrong detailDesc");
        assertEquals("somewhen", taskResult.taskDetails,
                "Loaded wrong taskDetails");
        assertEquals(true, taskResult.isDone,
                "Loaded wrong isDone");

        //DEADLINE this/by 19/09/2019 1015@|@false
        taskResult = taskListResult.getList().get(2);
        assertEquals(TaskType.DEADLINE, taskResult.taskType,
                "Loaded wrong taskType");
        assertEquals("this", taskResult.taskName,
                "Loaded wrong taskName");
        assertEquals("by", taskResult.detailDesc,
                "Loaded wrong detailDesc");
        assertEquals("19/09/2019 1015", taskResult.taskDetails,
                "Loaded wrong taskDetails");
        assertEquals(false, taskResult.isDone,
                "Loaded wrong isDone");
        /** TODO: Make Task Class Abstract
         assertEquals("19/09/2019 1015", taskResult.getDatetime(),
         "Loaded wrong taskDetails"); */
    }

    @Test
    void saveData() {
        // testDataLoad is the test Data
        // Follow the Storage Format when inputting new test cases
        Storage storageExpected = new Storage("testDataLoad.txt");
        Storage storageSaved = new Storage("testDataSave.txt");
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
