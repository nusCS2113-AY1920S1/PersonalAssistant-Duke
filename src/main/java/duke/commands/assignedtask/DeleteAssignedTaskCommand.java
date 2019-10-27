package duke.commands.assignedtask;

import duke.commands.Command;
import duke.exceptions.DukeException;
import duke.models.tasks.TaskManager;
import duke.util.Ui;
import duke.models.patients.Patient;
import duke.models.patients.PatientManager;
import duke.models.assignedtasks.AssignedTaskManager;
import duke.storages.StorageManager;

import java.util.ArrayList;

public class DeleteAssignedTaskCommand implements Command {
    private int patientId;
    private int taskId;
    private String deletedPatientInfo;
    private String[] command;


    /**
     * .
     *
     * @param deleteInfo .
     * @throws DukeException .
     */
    public DeleteAssignedTaskCommand(String[] deleteInfo) throws DukeException {

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
            throw new DukeException("Try to follow the format: delete assigned task %<taskUniqueID>/#<patientID>/"
                + "<patientName>");
        }
    }

    /**
     * .
     *
     * @param assignedTaskManager .
     * @param taskManager         .
     * @param patientManager      .
     * @param ui                  .
     * @param storageManager      .
     * @throws DukeException .
     */
    @Override
    public void execute(AssignedTaskManager assignedTaskManager, TaskManager taskManager,
                        PatientManager patientManager, Ui ui,
                        StorageManager storageManager)
        throws DukeException {

        if (patientId != 0) {
            try {
                Patient toBeDeletedPatient = patientManager.getPatient(patientId);
                if (assignedTaskManager.doesPatientIdExist(patientId)) {
                    assignedTaskManager.deleteAllTasksBelongToThePatient(patientId);
                    storageManager.saveAssignedTasks(assignedTaskManager.getAssignTasks());
                    ui.patientTaskAllDeleted(patientManager.getPatient(patientId));
                } else {
                    throw new DukeException("This Patient does not have any tasks.");
                }
            } catch (DukeException e) {
                throw new DukeException(e.getMessage());
            }
        } else if (taskId != 0) {
            try {
                assignedTaskManager.deletePatientTaskByUniqueId(taskId);
                storageManager.saveAssignedTasks(assignedTaskManager.getAssignTasks());
                ui.patientTaskDeleted(taskId);
            } catch (DukeException e) {
                throw new DukeException("Task is not found!");
            }
        } else {
            try {
                String deletePatientName = this.deletedPatientInfo;
                ArrayList<Patient> patientsWithSameName = patientManager.getPatientByName(deletePatientName);
                int tempPatientId = patientsWithSameName.get(0).getId();
                if (assignedTaskManager.doesPatientIdExist(tempPatientId)) {
                    assignedTaskManager.deleteAllTasksBelongToThePatient(tempPatientId);
                    storageManager.saveAssignedTasks(assignedTaskManager.getAssignTasks());
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
     *
     * @return .
     */
    @Override
    public boolean isExit() {
        return false;
    }
}