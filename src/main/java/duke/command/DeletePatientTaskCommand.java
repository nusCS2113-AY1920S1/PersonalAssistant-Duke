package duke.command;

import duke.core.DukeException;
import duke.core.Ui;
import duke.patient.Patient;
import duke.patient.PatientManager;
import duke.relation.PatientTask;
import duke.relation.PatientTaskList;
import duke.storage.StorageManager;
import duke.task.TaskManager;

import java.util.ArrayList;

public class DeletePatientTaskCommand implements Command {
    private int patientId;
    private int taskId;
    private String deletedPatientInfo;
    private String[] command;


    /**
     *  .
     * @param deleteInfo .
     * @throws DukeException .
     */
    public DeletePatientTaskCommand(String[] deleteInfo) throws DukeException {

        char firstChar = deleteInfo[0].charAt(0);
        try {
            if (firstChar == '#') {
                this.patientId = Integer.parseInt(deleteInfo[0].replace("#","").trim());
                this.taskId = Integer.parseInt(deleteInfo[1]);
            } else {
                this.deletedPatientInfo = deleteInfo[0];
                this.taskId = Integer.parseInt(deleteInfo[1]);
            }
        } catch (Exception e) {
            throw new DukeException("Try to follow the format: delete patienttask #patientid taskuniqeid");
        }

    }

    /**
     *  .
     * @param patientTaskList .
     * @param tasks .
     * @param patientManager .
     * @param ui .
     * @param storageManager .
     * @throws DukeException .
     */
    @Override
    public void execute(PatientTaskList patientTaskList, TaskManager tasks, PatientManager patientManager, Ui ui,
                        StorageManager storageManager)
            throws DukeException {
        if (patientId != 0) {
            try {
                Patient toBeDeletedPatient = patientManager.getPatient(patientId);
                for (PatientTask patientTask: patientTaskList.getPatientTask(patientId)) {
                    if (patientTask.getUid() == taskId) {
                        patientTaskList.deletePatientTaskByUniqueId(taskId);
                        storageManager.saveAssignedTasks(patientTaskList.fullPatientTaskList());
                        ui.patientTaskDeleted(patientTask, toBeDeletedPatient);
                    }
                }
            } catch (DukeException e) {
                throw new DukeException("Task or patient is not found!" + e.getMessage());
            }
        } else {
            try {
                String patientName = this.deletedPatientInfo;
                ArrayList<Patient> patientsWithSameName = patientManager.getPatientByName(patientName);
                ArrayList<PatientTask> toBeDeleted =
                        patientTaskList.getPatientTask(patientsWithSameName.get(0).getID());
                for (PatientTask patientTask: toBeDeleted) {
                    if (patientTask.getUid() == taskId) {
                        patientTaskList.deletePatientTaskByUniqueId(taskId);
                        storageManager.saveAssignedTasks(patientTaskList.fullPatientTaskList());
                        ui.patientTaskDeleted(patientTask, patientsWithSameName.get(0));
                    }
                }
            } catch (Exception e) {
                throw new DukeException("Task or patient is not found!");
            }
        }
    }

    /**
     * .
     * @return .
     */
    @Override
    public boolean isExit() {
        return false;
    }
}