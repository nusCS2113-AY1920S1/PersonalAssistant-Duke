package duke.command;

import duke.core.DateTimeParser;
import duke.core.DukeException;
import duke.core.Ui;
import duke.patient.Patient;
import duke.patient.PatientManager;
import duke.relation.EventPatientTask;
import duke.relation.PatientTask;
import duke.relation.StandardPatientTask;
import duke.storage.PatientStorage;
import duke.storage.PatientTaskStorage;
import duke.storage.TaskStorage;
import duke.relation.PatientTaskList;
import duke.task.Task;
import duke.task.TaskManager;


import java.time.LocalDateTime;
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
    public DeletePatientTaskCommand(String deleteInfo) throws DukeException {

        char firstChar = deleteInfo.charAt(0);
        try {
            if (firstChar == '#') {
                this.patientId = Integer.parseInt(deleteInfo.replace("#","").trim());
            } else if (firstChar == '%') {
                this.taskId = Integer.parseInt(deleteInfo.replace("%","").trim());
            } else {
                this.deletedPatientInfo = deleteInfo;
            }
        } catch (Exception e) {
            throw new DukeException("Try to follow the format: delete patienttask %<taskUniqueID>/#<patientID>" +
                    "/<patientName>. ");
        }

    }

    /**
     *  .
     * @param patientTaskList .
     * @param tasks .
     * @param patientManager .
     * @param ui .
     * @param patientTaskStorage .
     * @param taskStorage .
     * @param patientStorage .
     * @throws DukeException .
     */
    @Override
    public void execute(PatientTaskList patientTaskList, TaskManager tasks, PatientManager patientManager, Ui ui,
                        PatientTaskStorage patientTaskStorage, TaskStorage taskStorage, PatientStorage patientStorage)
            throws DukeException {
        if (patientId != 0) {
            try {
                Patient toBeDeletedPatient = patientManager.getPatient(patientId);
                if (patientTaskList.isPatientIdExist(patientId)) {
                    patientTaskList.deleteEntirePatientTask(patientId);
                    patientTaskStorage.save(patientTaskList.fullPatientTaskList());
                    ui.patientTaskAllDeleted(patientManager.getPatient(patientId));
                }
            } catch (DukeException e) {
                throw new DukeException("Patient is not found!");
            }
        } else if (taskId != 0) {
            try {
                patientTaskList.deletePatientTaskByUniqueId(taskId);
                patientTaskStorage.save(patientTaskList.fullPatientTaskList());
                ui.patientTaskDeleted(taskId);
            } catch (DukeException e) {
                throw new DukeException("Task is not found!");
            }
        } else {
            try {
                String patientName = this.deletedPatientInfo;
                ArrayList<Patient> patientsWithSameName = patientManager.getPatientByName(patientName);
                if (patientTaskList.isPatientIdExist(patientsWithSameName.get(0).getID())) {
                    patientTaskList.deleteEntirePatientTask(patientsWithSameName.get(0).getID());
                    patientTaskStorage.save(patientTaskList.fullPatientTaskList());
                    ui.patientTaskAllDeleted(patientManager.getPatient(patientsWithSameName.get(0).getID()));
                }
            } catch (Exception e) {
                throw new DukeException("Patient is not found!");
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