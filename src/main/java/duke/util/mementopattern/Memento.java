//@@author WEIFENG-NUSCEG

package duke.util.mementopattern;

import duke.models.patients.PatientManager;
import duke.models.assignedtasks.AssignedTaskManager;
import duke.models.tasks.TaskManager;

/**
 * This is an object created to save the internal state of the our TaskManager, PatientManager,
 * AssignedTaskManager.
 */
public class Memento {
    private TaskManager memTaskList;
    private PatientManager memPatientManager;
    private AssignedTaskManager memAssignedTaskManager;

    /**
     * Create a new memento object to save the internal state of the data.
     * @param assignedTaskManager    a AssignedTask Manger contains
     * @param taskManager            a Task Manager
     * @param patientManager         a Patient Manager
     */
    public Memento(TaskManager taskManager, AssignedTaskManager assignedTaskManager, PatientManager patientManager) {
        this.memTaskList = taskManager;
        this.memPatientManager = patientManager;
        this.memAssignedTaskManager = assignedTaskManager;
    }

    /**
     * Retrieve the internal state of the TaskManager.
     * @return a TaskManager stored in the Memento object
     */
    public TaskManager getTaskState() {
        return this.memTaskList;
    }

    /**
     * Retrieve the internal state of the PatientManager.
     * @return a PatientManager stored in the Memento object
     */
    public PatientManager getPatientState() {
        return this.memPatientManager;
    }

    /**
     * Retrieve the internal state of the AssignedTaskManager.
     * @return a AssignedTaskManager stored in the Memento object
     */
    public AssignedTaskManager getPatientTaskState() {
        return this.memAssignedTaskManager;
    }




}