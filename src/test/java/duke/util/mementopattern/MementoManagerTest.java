//@@author WEIFENG-NUSCEG
package duke.util.mementopattern;

import duke.exceptions.DukeException;
import duke.models.assignedtasks.AssignedTask;
import duke.models.assignedtasks.AssignedTaskManager;
import duke.models.assignedtasks.AssignedTaskWithDate;
import duke.models.assignedtasks.AssignedTaskWithPeriod;
import duke.models.patients.Patient;
import duke.models.patients.PatientManager;
import duke.models.tasks.Task;
import duke.models.tasks.TaskManager;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.util.ArrayList;

public class MementoManagerTest {

    @Test
    public void addTest() throws DukeException {
        ArrayList<AssignedTask> assignedTasksTest = new ArrayList<>();
        AssignedTask assignedTask1 = new AssignedTaskWithPeriod(4,8,false,false,
                "22/10/2014 1200","06/01/2019 1200","period",1);
        AssignedTask assignedTask7 = new AssignedTaskWithDate(7,11,false,false,
                "07/12/2011 1200","deadline",2);
        assignedTasksTest.add(assignedTask1);
        assignedTasksTest.add(assignedTask7);
        AssignedTaskManager assignedTaskManagerTest = new AssignedTaskManager(assignedTasksTest);

        ArrayList<Patient> testPatientList = new ArrayList<>();
        Patient patient1 = new Patient(1,"Lisa Chew","S1234567T","12A","Nil");
        testPatientList.add(patient1);
        Patient patient2 = new Patient(2,"Helen Teo","S2234567I","3B","Nil");
        testPatientList.add(patient2);
        PatientManager patientManager = new PatientManager(testPatientList);

        ArrayList<Task> testTaskList = new ArrayList<>();
        Task task1 = new Task(1,"Take medicine Panadol");
        testTaskList.add(task1);
        Task task2 = new Task(2,"Do injection");
        testTaskList.add(task2);
        Task task3 = new Task(3,"Meeting with doctor");
        testTaskList.add(task3);
        Task task4 = new Task(4,"Take medicine Decolgen");
        testTaskList.add(task4);
        TaskManager taskManager = new TaskManager(testTaskList);

        Memento mementoTest = new Memento(taskManager, assignedTaskManagerTest, patientManager);
        MementoManager mementoManager = new MementoManager();
        mementoManager.add(mementoTest);
        assertTrue(mementoManager.pop() == mementoTest);
    }

    @Test
    public void popTest() throws DukeException {
        ArrayList<AssignedTask> assignedTasksTest = new ArrayList<>();
        AssignedTask assignedTask1 = new AssignedTaskWithPeriod(4,8,false,false,
                "22/10/2014 1200","06/01/2019 1200","period",1);
        AssignedTask assignedTask7 = new AssignedTaskWithDate(7,11,false,false,
                "07/12/2011 1200","deadline",2);
        assignedTasksTest.add(assignedTask1);
        assignedTasksTest.add(assignedTask7);
        AssignedTaskManager assignedTaskManagerTest = new AssignedTaskManager(assignedTasksTest);

        ArrayList<Patient> testPatientList = new ArrayList<>();
        Patient patient1 = new Patient(1,"Lisa Chew","S1234567T","12A","Nil");
        testPatientList.add(patient1);
        Patient patient2 = new Patient(2,"Helen Teo","S2234567I","3B","Nil");
        testPatientList.add(patient2);
        PatientManager patientManager = new PatientManager(testPatientList);

        ArrayList<Task> testTaskList = new ArrayList<>();
        Task task1 = new Task(1,"Take medicine Panadol");
        testTaskList.add(task1);
        Task task2 = new Task(2,"Do injection");
        testTaskList.add(task2);
        Task task3 = new Task(3,"Meeting with doctor");
        testTaskList.add(task3);
        Task task4 = new Task(4,"Take medicine Decolgen");
        testTaskList.add(task4);
        TaskManager taskManager = new TaskManager(testTaskList);

        Memento mementoTest = new Memento(taskManager, assignedTaskManagerTest, patientManager);
        MementoManager mementoManager = new MementoManager();
        mementoManager.add(mementoTest);
        assertTrue(mementoManager.pop() == mementoTest);
    }

    @Test
    public void saveDukeStateToMementoTest() throws DukeException {
        ArrayList<AssignedTask> assignedTasksTest = new ArrayList<>();
        AssignedTask assignedTask1 = new AssignedTaskWithPeriod(4,8,false,false,
                "22/10/2014 1200","06/01/2019 1200","period",1);
        AssignedTask assignedTask7 = new AssignedTaskWithDate(7,11,false,false,
                "07/12/2011 1200","deadline",2);
        assignedTasksTest.add(assignedTask1);
        assignedTasksTest.add(assignedTask7);
        AssignedTaskManager assignedTaskManagerTest = new AssignedTaskManager(assignedTasksTest);

        ArrayList<Patient> testPatientList = new ArrayList<>();
        Patient patient1 = new Patient(1,"Lisa Chew","S1234567T","12A","Nil");
        testPatientList.add(patient1);
        Patient patient2 = new Patient(2,"Helen Teo","S2234567I","3B","Nil");
        testPatientList.add(patient2);
        PatientManager patientManager = new PatientManager(testPatientList);

        ArrayList<Task> testTaskList = new ArrayList<>();
        Task task1 = new Task(1,"Take medicine Panadol");
        testTaskList.add(task1);
        Task task2 = new Task(2,"Do injection");
        testTaskList.add(task2);
        Task task3 = new Task(3,"Meeting with doctor");
        testTaskList.add(task3);
        Task task4 = new Task(4,"Take medicine Decolgen");
        testTaskList.add(task4);
        TaskManager taskManager = new TaskManager(testTaskList);

        MementoManager mementoManager = new MementoManager();
        assertNotNull(mementoManager.saveDukeStateToMemento(taskManager, assignedTaskManagerTest,
                patientManager).getPatientState());
    }
}
