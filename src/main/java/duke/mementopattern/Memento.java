package duke.mementopattern;

import duke.patient.PatientManager;
import duke.relation.PatientTaskList;
import duke.task.TaskManager;

public class Memento {
    /**
     *  .
     * @return .
     */
    private TaskManager memTaskList;
    private PatientManager memPatientManager;
    private PatientTaskList memPatientTaskList;

    /**
     *  .
     * @Param .
     */
    public Memento(TaskManager taskManager, PatientTaskList patientTaskList, PatientManager patientManager) {
        this.memTaskList = taskManager;
        this.memPatientManager = patientManager;
        this.memPatientTaskList = patientTaskList;
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
    public PatientTaskList getPatientTaskState() {
        return this.memPatientTaskList;
    }
}