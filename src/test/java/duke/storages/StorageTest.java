//@@author HUANGXUANKUN

package duke.storages;

import duke.exceptions.DukeException;
import duke.models.assignedtasks.AssignedTask;
import duke.models.patients.Patient;

import duke.models.tasks.Task;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;

/**
 * Junit test for Storage.
 *
 * @author HUANGXUANKUN
 * @version 1.4
 */
class StorageTest {
    private Path resourceDirectory = Paths.get("src","test","Testdata");
    private String testDataPath = resourceDirectory.toFile().getAbsolutePath();
    private StorageManager storageManager = new StorageManager(testDataPath);

    @Test
    void readPatientsTest() {
        ArrayList<Patient> patients;
        try {
            patients = storageManager.loadPatients();
            assertEquals(3, patients.size(),
                "3 patients are expected to be loaded, instead of "
                    + patients.size());
            Patient firstPatient = patients.get(0);
            assertEquals(1, firstPatient.getId(),
                "Id is expected to be 1 instead of "
                    + firstPatient.getId());
            assertEquals("John Doe", firstPatient.getName(),
                "Name is expected to be 'John Doe' instead of "
                    + firstPatient.getName());
            assertEquals("G00012345L", firstPatient.getNric(),
                "NRIC is expected to be 'G00012345L' instead of "
                    + firstPatient.getNric());
            assertEquals("3A-1", firstPatient.getRoom(), "Room is expected to be '3A-1' instead of "
                + firstPatient.getRoom());
            assertEquals("Hasn't had an appetite for past 2 days", firstPatient.getRemark(),
                "Remark is expected to be 'Hasn't had an appetite for past 2 days' instead of "
                    + firstPatient.getRemark());
        } catch (DukeException e) {
            fail("Loading patient from patients.csv fails");
        }
    }

    @Test
    void readTasksTest() {
        try {
            ArrayList<Task> tasks = storageManager.loadTasks();
            Task firstTask = tasks.get(0);

            assertEquals(3, tasks.size(),
                "3 tasks are expected to be loaded, instead of "
                    + tasks.size());
            assertEquals(1, firstTask.getId(),
                "Id is expected to be 1 instead of "
                    + firstTask.getId());
            assertEquals("Take Medicine A", firstTask.getDescription(),
                "Description is expected to be 'Take Medicine A' instead of "
                    + firstTask.getDescription());
        } catch (DukeException e) {
            fail("Loading task from tasks.csv fails");
        }
    }

    @Test
    void readAssignedTasksTest() {
        try {
            ArrayList<AssignedTask> assignedTasks = storageManager.loadAssignedTasks();
            AssignedTask firstAssignedTask = assignedTasks.get(0);
            assertEquals(1, assignedTasks.size(),
                "One assigned task is expected to be loaded, instead of "
                    + assignedTasks.size());
            assertEquals(1, firstAssignedTask.getPid());
            assertEquals(1, firstAssignedTask.getTid());
            assertFalse(firstAssignedTask.getIsDone());
            assertFalse(firstAssignedTask.getIsRecursive());
            assertEquals("null", firstAssignedTask.getTodoDateRaw());
            assertEquals("01/11/2019 1600", firstAssignedTask.getStartDateRaw());
            assertEquals("02/11/2019 1600", firstAssignedTask.getEndDateRaw());
            assertEquals("period", firstAssignedTask.getType());
            assertEquals(15, firstAssignedTask.getUuid());
        } catch (DukeException e) {
            fail("Loading task from assignedTasks.csv fails");
        }
    }

    @Test
    void readCounterTest() {
        try {
            Map<String, Integer> commandFrequency = storageManager.loadCommandFrequency();
            assertEquals(5, commandFrequency.size(),
                "5 command-frequency records is expected, instead of "
                    + commandFrequency.size());
            Map.Entry<String,Integer> entry = commandFrequency.entrySet().iterator().next();
            String firstKey = entry.getKey();
            int firstValue = entry.getValue();
            assertEquals("DeleteTaskCommand", firstKey);
            assertEquals(8, firstValue);

        } catch (DukeException e) {
            fail("Loading task from counter.csv fails");
        }
    }
}
