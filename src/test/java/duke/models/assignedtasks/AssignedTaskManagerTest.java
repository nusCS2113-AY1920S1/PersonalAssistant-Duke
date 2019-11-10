//@@author WEIFENG-NUSCEG
package duke.models.assignedtasks;

import duke.Duke;
import duke.exceptions.DukeException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;


public class AssignedTaskManagerTest {

    @Test
    public void getAssignedTaskTest() throws DukeException {
        ArrayList<AssignedTask> assignedTasksTest = new ArrayList<>();
        AssignedTask assignedTask1 = new AssignedTaskWithPeriod(4,8,false,false,
                "22/10/2014 1200","06/01/2019 1200","period",1);
        AssignedTask assignedTask7 = new AssignedTaskWithDate(7,11,false,false,
                "07/12/2011 1200","deadline",2);
        assignedTasksTest.add(assignedTask1);
        assignedTasksTest.add(assignedTask7);
        AssignedTaskManager assignedTaskManagerTest = new AssignedTaskManager(assignedTasksTest);
        assertEquals(assignedTaskManagerTest.getAssignTasks(),assignedTasksTest);
    }

    @Test
    public void addPatientTaskTest() throws DukeException {
        ArrayList<AssignedTask> assignedTasksTest = new ArrayList<>();
        AssignedTask assignedTask1 = new AssignedTaskWithPeriod(4,8,false,false,
                "22/10/2014 1200","06/01/2019 1200","period",1);
        AssignedTask assignedTask7 = new AssignedTaskWithDate(6,11,false,false,
                "07/12/2011 1200","deadline",2);
        assignedTasksTest.add(assignedTask1);
        assignedTasksTest.add(assignedTask7);
        AssignedTaskManager assignedTaskManagerTest = new AssignedTaskManager(assignedTasksTest);
        AssignedTask assignedTask2 = new AssignedTaskWithPeriod(42,3,false,false,
                "11/11/2013 1200","06/08/2019 1200","period");
        assignedTaskManagerTest.addPatientTask(assignedTask2);
        AssignedTask assignedTask100 = new AssignedTaskWithPeriod(42,3,false,false,
                "11/11/2013 1200","06/08/2019 1200","period");
        assignedTaskManagerTest.addPatientTask(assignedTask100);
        assertEquals(assignedTaskManagerTest.getAssignTasks().size() , 4);
    }

    @Test
    public void deletePatientTaskByUniqueIdTest() throws DukeException {
        ArrayList<AssignedTask> assignedTasksTest = new ArrayList<>();
        AssignedTask assignedTask2 = new AssignedTaskWithPeriod(42,3,false,false,
                "11/11/2013 1200","06/08/2019 1200","period",1);
        AssignedTask assignedTask3 = new AssignedTaskWithPeriod(7,100,false,false,
                "06/11/2011 1200","11/11/2019 1200","period",2);
        assignedTasksTest.add(assignedTask2);
        assignedTasksTest.add(assignedTask3);
        AssignedTaskManager assignedTaskManagerTest = new AssignedTaskManager(assignedTasksTest);
        assignedTaskManagerTest.deletePatientTaskByUniqueId(1);
        assertTrue(assignedTaskManagerTest.getAssignTasks().size() == 1);
    }

    @Test
    public void deleteAssignedTaskByTaskIdTest() throws DukeException {
        ArrayList<AssignedTask> assignedTasksTest = new ArrayList<>();
        AssignedTask assignedTask2 = new AssignedTaskWithPeriod(42,3,false,false,
                "11/11/2013 1200","06/08/2019 1200","period",1);
        AssignedTask assignedTask3 = new AssignedTaskWithPeriod(7,100,false,false,
                "06/11/2011 1200","11/11/2019 1200","period",2);
        assignedTasksTest.add(assignedTask2);
        assignedTasksTest.add(assignedTask3);
        AssignedTaskManager assignedTaskManagerTest = new AssignedTaskManager(assignedTasksTest);
        assignedTaskManagerTest.deleteAllAssignedTaskByTaskId(3);
        assertTrue(assignedTaskManagerTest.getAssignTasks().size() == 1);
    }

    @Test
    public void deleteAllAssignedTaskByTaskIdTest() throws DukeException {
        ArrayList<AssignedTask> assignedTasksTest = new ArrayList<>();
        AssignedTask assignedTask2 = new AssignedTaskWithPeriod(42,3,false,false,
                "11/11/2013 1200","06/08/2019 1200","period");
        AssignedTask assignedTask3 = new AssignedTaskWithPeriod(7,3,false,false,
                "06/11/2011 1200","11/11/2019 1200","period");
        assignedTasksTest.add(assignedTask2);
        assignedTasksTest.add(assignedTask3);
        AssignedTaskManager assignedTaskManagerTest = new AssignedTaskManager(assignedTasksTest);
        assignedTaskManagerTest.deleteAllAssignedTaskByTaskId(3);
        assertTrue(assignedTaskManagerTest.getAssignTasks().size() == 0);
    }

    @Test
    public void doesPatientIdExistTest() throws DukeException {
        ArrayList<AssignedTask> assignedTasksTest = new ArrayList<>();
        AssignedTask assignedTask2 = new AssignedTaskWithPeriod(42,3,false,false,
                "11/11/2013 1200","06/08/2019 1200","period");
        AssignedTask assignedTask3 = new AssignedTaskWithPeriod(7,3,false,false,
                "06/11/2011 1200","11/11/2019 1200","period");
        assignedTasksTest.add(assignedTask2);
        assignedTasksTest.add(assignedTask3);
        AssignedTaskManager assignedTaskManagerTest = new AssignedTaskManager(assignedTasksTest);
        assertFalse(assignedTaskManagerTest.doesPatientIdExist(8));
        assertTrue(assignedTaskManagerTest.doesPatientIdExist(7));
    }

    @Test
    public void doesUidExistTest() throws DukeException {
        ArrayList<AssignedTask> assignedTasksTest = new ArrayList<>();
        AssignedTask assignedTask2 = new AssignedTaskWithPeriod(42,3,false,false,
                "11/11/2013 1200","06/08/2019 1200","period",3);
        AssignedTask assignedTask3 = new AssignedTaskWithPeriod(7,3,false,false,
                "06/11/2011 1200","11/11/2019 1200","period",1);
        assignedTasksTest.add(assignedTask2);
        assignedTasksTest.add(assignedTask3);
        AssignedTaskManager assignedTaskManagerTest = new AssignedTaskManager(assignedTasksTest);
        assertFalse(assignedTaskManagerTest.doesUidExist(8));
        assertTrue(assignedTaskManagerTest.doesUidExist(1));
    }

    @Test
    public void isSameStartEndTimeExistTest() throws DukeException {
        ArrayList<AssignedTask> assignedTasksTest = new ArrayList<>();
        AssignedTask assignedTask2 = new AssignedTaskWithPeriod(42,3,false,false,
                "11/11/2013 1200","06/08/2019 1200","period",1);
        AssignedTask assignedTask3 = new AssignedTaskWithPeriod(7,3,false,false,
                "06/11/2011 1200","11/11/2019 1200","period",2);
        AssignedTask assignedTask100 = new AssignedTaskWithPeriod(42,3,false,false,
                "11/11/2013 1200","06/08/2019 1200","period");
        assignedTasksTest.add(assignedTask2);
        assignedTasksTest.add(assignedTask3);
        AssignedTask assignedTask4 = new AssignedTaskWithPeriod(7,11,false,false,
                "05/12/2011 1200","06/03/2019 1200","period");
        AssignedTaskManager assignedTaskManagerTest = new AssignedTaskManager(assignedTasksTest);
        assertTrue(assignedTaskManagerTest.isSameStartEndTimeExist(assignedTask100));
        assertFalse(assignedTaskManagerTest.isSameStartEndTimeExist(assignedTask4));
    }

    @Test
    public void isSameDeadlineExistTest() throws DukeException {
        ArrayList<AssignedTask> assignedTasksTest = new ArrayList<>();
        AssignedTask assignedTask2 = new AssignedTaskWithPeriod(42,3,false,false,
                "11/11/2013 1200","06/08/2019 1200","period");
        AssignedTask assignedTask3 = new AssignedTaskWithPeriod(7,3,false,false,
                "06/11/2011 1200","11/11/2019 1200","period");
        AssignedTask assignedTask5 = new AssignedTaskWithDate(7,11,false,false,
                "05/02/2011 1200","deadline");
        assignedTasksTest.add(assignedTask2);
        assignedTasksTest.add(assignedTask3);
        assignedTasksTest.add(assignedTask5);
        AssignedTask assignedTask6 = new AssignedTaskWithDate(7,11,false,false,
                "05/02/2011 1200","deadline");
        AssignedTask assignedTask7 = new AssignedTaskWithDate(7,11,false,false,
                "05/12/2011 1100","deadline");
        AssignedTaskManager assignedTaskManagerTest = new AssignedTaskManager(assignedTasksTest);
        assertTrue(assignedTaskManagerTest.isSameDeadlineExist(assignedTask6));
        assertFalse(assignedTaskManagerTest.isSameStartEndTimeExist(assignedTask7));
    }

    @Test
    public void deleteAssignedTaskBelongToThePatientFromUniqueIdMapTest() throws DukeException {
        ArrayList<AssignedTask> assignedTasksTest = new ArrayList<>();
        AssignedTask assignedTask2 = new AssignedTaskWithPeriod(42,3,false,false,
                "11/11/2013 1200","06/08/2019 1200","period");
        AssignedTask assignedTask3 = new AssignedTaskWithPeriod(7,3,false,false,
                "06/11/2011 1200","11/11/2019 1200","period");
        assignedTasksTest.add(assignedTask2);
        assignedTasksTest.add(assignedTask3);
        AssignedTaskManager assignedTaskManagerTest = new AssignedTaskManager(assignedTasksTest);
        assignedTaskManagerTest.deleteAssignedTaskBelongToThePatientFromUniqueIdMap(42);
        assertTrue(assignedTaskManagerTest.getAssignTasks().size() == 1);
    }

    @Test
    public void deleteAllTasksBelongToThePatientTest() throws DukeException {
        ArrayList<AssignedTask> assignedTasksTest = new ArrayList<>();
        AssignedTask assignedTask2 = new AssignedTaskWithPeriod(42,3,false,false,
                "11/11/2013 1200","06/08/2019 1200","period",1);
        AssignedTask assignedTask3 = new AssignedTaskWithPeriod(7,3,false,false,
                "06/11/2011 1200","11/11/2019 1200","period",2);
        AssignedTask assignedTask4 = new AssignedTaskWithPeriod(42,3,false,false,
                "11/11/2013 1200","06/08/2019 1200","period",3);
        assignedTasksTest.add(assignedTask2);
        assignedTasksTest.add(assignedTask3);
        assignedTasksTest.add(assignedTask4);
        AssignedTaskManager assignedTaskManagerTest = new AssignedTaskManager(assignedTasksTest);
        assignedTaskManagerTest.deleteAllTasksBelongToThePatient(42);
        assertTrue(assignedTaskManagerTest.getAssignTasks().size() == 1);
    }

    @Test
    public void getPatientTaskTest() throws DukeException {
        ArrayList<AssignedTask> assignedTasksTest = new ArrayList<>();
        AssignedTask assignedTask2 = new AssignedTaskWithPeriod(42,3,false,false,
                "11/11/2013 1200","06/08/2019 1200","period");
        AssignedTask assignedTask3 = new AssignedTaskWithPeriod(7,3,false,false,
                "06/11/2011 1200","11/11/2019 1200","period");
        AssignedTask assignedTask4 = new AssignedTaskWithPeriod(42,3,false,false,
                "11/11/2013 1200","06/08/2019 1200","period");
        assignedTasksTest.add(assignedTask2);
        assignedTasksTest.add(assignedTask3);
        assignedTasksTest.add(assignedTask4);
        AssignedTaskManager assignedTaskManagerTest = new AssignedTaskManager(assignedTasksTest);
        assertTrue(assignedTaskManagerTest.getPatientTask(42).size() == 2);

    }

    @Test
    public void getTaskPatientTest() throws DukeException {
        ArrayList<AssignedTask> assignedTasksTest = new ArrayList<>();
        AssignedTask assignedTask2 = new AssignedTaskWithPeriod(42,3,false,false,
                "11/11/2013 1200","06/08/2019 1200","period");
        AssignedTask assignedTask3 = new AssignedTaskWithPeriod(7,3,false,false,
                "06/11/2011 1200","11/11/2019 1200","period");
        AssignedTask assignedTask4 = new AssignedTaskWithPeriod(42,3,false,false,
                "11/11/2013 1200","06/08/2019 1200","period");
        assignedTasksTest.add(assignedTask2);
        assignedTasksTest.add(assignedTask3);
        assignedTasksTest.add(assignedTask4);
        AssignedTaskManager assignedTaskManagerTest = new AssignedTaskManager(assignedTasksTest);
        assertTrue(assignedTaskManagerTest.getTaskPatient(3).size() == 3);
    }
}
