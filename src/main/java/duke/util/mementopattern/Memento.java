package duke.util.mementopattern;

import duke.models.patients.PatientManager;
import duke.models.assignedtasks.AssignedTaskManager;
import duke.models.tasks.TaskManager;

public class Memento {
    /**
     *  .
     * @return .
     */
    private TaskManager memTaskList;
    private PatientManager memPatientManager;
    private AssignedTaskManager memAssignedTaskManager;

    /**
     *  .
     * @Param .
     */
    public Memento(TaskManager taskManager, AssignedTaskManager assignedTaskManager, PatientManager patientManager) {
        this.memTaskList = taskManager;
        this.memPatientManager = patientManager;
        this.memAssignedTaskManager = assignedTaskManager;
    }

    /**
     *  .
     * @return .
     */
    public TaskManager getTaskState() {
        return this.memTaskList;
    }

    /**
     *  .
     * @return .
     */
    public PatientManager getPatientState() {
        return this.memPatientManager;
    }

    /**
     *  .
     * @return .
     */
    public AssignedTaskManager getPatientTaskState() {
        return this.memAssignedTaskManager;
    }
}