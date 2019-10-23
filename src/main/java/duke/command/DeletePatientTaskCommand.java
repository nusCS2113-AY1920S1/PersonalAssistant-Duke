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

public class DeletePatientTaskCommand extends Command {
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
                this.patientId = Integer.parseInt(deleteInfo[0].substring(1));
            } else if (firstChar == '%') {
                this.taskId = Integer.parseInt(deleteInfo[0].substring(1));
            } else {
                this.deletedPatientInfo = deleteInfo[0];
            }
        } catch (Exception e) {
            throw new DukeException("Try to follow the format: delete patienttask %<taskUniqueID>/#<patientID>/"
                    + "<patientName>");
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
                if (patientTaskList.doesPatientIdExist(patientId)) {
                    patientTaskList.deleteAllTasksBelongToThePatient(patientId);
                    storageManager.saveAssignedTasks(patientTaskList.fullPatientTaskList());
                    ui.patientTaskAllDeleted(patientManager.getPatient(patientId));
                } else {
                    throw new DukeException("This Patient does not have any tasks.");
                }
            } catch (DukeException e) {
                throw new DukeException(e.getMessage());
            }
        } else if (taskId != 0) {
            try {
                patientTaskList.deletePatientTaskByUniqueId(taskId);
                storageManager.saveAssignedTasks(patientTaskList.fullPatientTaskList());
                ui.patientTaskDeleted(taskId);
            } catch (DukeException e) {
                throw new DukeException("Task is not found!");
            }
        } else {
            try {
                String deletePatientName = this.deletedPatientInfo;
                ArrayList<Patient> patientsWithSameName = patientManager.getPatientByName(deletePatientName);
                int tempPatientId = patientsWithSameName.get(0).getID();
                if (patientTaskList.doesPatientIdExist(tempPatientId)) {
                    patientTaskList.deleteAllTasksBelongToThePatient(tempPatientId);
                    storageManager.saveAssignedTasks(patientTaskList.fullPatientTaskList());
                    ui.patientTaskAllDeleted(patientManager.getPatient(tempPatientId));
                } else {
                    throw new DukeException("This Patient does not have any tasks.");
                }
            } catch (Exception e) {
                throw new DukeException(e.getMessage());
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