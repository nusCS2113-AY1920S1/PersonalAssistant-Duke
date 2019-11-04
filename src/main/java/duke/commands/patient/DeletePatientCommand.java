//@@author kkeejjuunn

package duke.commands.patient;

import duke.commands.Command;
import duke.exceptions.DukeException;
import duke.util.DukeUi;
import duke.models.patients.Patient;
import duke.models.patients.PatientManager;
import duke.models.assignedtasks.AssignedTask;
import duke.models.assignedtasks.AssignedTaskManager;
import duke.models.tasks.Task;
import duke.models.tasks.TaskManager;
import duke.storages.StorageManager;

import java.util.ArrayList;

public class DeletePatientCommand implements Command {
    private int id;

    private String deletedPatientInfo;
    private Patient patientToBeDeleted;

    /**
     * It saves the delete patient command.
     *
     * @param deletedPatientInfo it contains the information of the patient to be deleted.
     */
    public DeletePatientCommand(String deletedPatientInfo) {
        this.deletedPatientInfo = deletedPatientInfo;
    }

    /**
     * It extracts patient id from the delete patient command.
     *
     * @param deletedPatientInfo contains the delete patient command.
     * @param dukeUi             allows user to choose the patient to delete.
     * @param patientManager     retrieves patient based on patient id.
     * @return                   patient to be deleted.
     * @throws DukeException     if no matched patient found.
     */
    public Patient getPatientByDeletePatientCommand(String deletedPatientInfo, DukeUi dukeUi,
                                                    PatientManager patientManager) throws DukeException {
        char firstChar = deletedPatientInfo.charAt(0);
        Patient patient = null;
        if (firstChar == '#') {
            int id;
            try {
                id = Integer.parseInt(deletedPatientInfo.substring(1));
            } catch (Exception e) {
                throw new DukeException(DeletePatientCommand.class,"The patient id is invalid. ");
            }
            try {
                patient = patientManager.getPatient(id);
            } catch (Exception e) {
                throw new DukeException(DeletePatientCommand.class, "The patient id does not exist. ");
            }
        } else {
            throw new DukeException(DeletePatientCommand.class,"Please follow format 'delete patient #<id>'. ");
        }
        return patient;
    }

    /**
     * It deletes the patient returned from getPatientByDeletePatientCommand.
     * It checks whether this patient is assigned to any tasks.
     * It deletes the relationship between this patient and any tasks.
     *
     * @param assignedTaskManager contains the information between all the tasks and patients.
     * @param taskManager         contains information of all the tasks.
     * @param patientManager      contains information of all the patients.
     * @param dukeUi              interacts with user.
     * @param storageManager      save the changes in csv file.
     * @throws DukeException      if there is error deleting the patient.
     */
    @Override
    public void execute(AssignedTaskManager assignedTaskManager, TaskManager taskManager, PatientManager patientManager,
                        DukeUi dukeUi, StorageManager storageManager) throws DukeException {

        try {
            patientToBeDeleted = getPatientByDeletePatientCommand(deletedPatientInfo, dukeUi, patientManager);
        } catch (Exception e) {
            throw e;
        }
        dukeUi.showPatientInfo(patientToBeDeleted);
        try {
            ArrayList<AssignedTask> patientTasks = assignedTaskManager.getPatientTask(patientToBeDeleted.getId());
            ArrayList<Task> assignedTasks = new ArrayList<>();
            for (AssignedTask patientTask : patientTasks) {
                assignedTasks.add(taskManager.getTask(patientTask.getTid()));
            }
            dukeUi.assignedTasksFoundWhenDeletePatient(patientToBeDeleted, assignedTasks);
            assignedTaskManager.deleteAllTasksBelongToThePatient(patientToBeDeleted.getId());
            storageManager.saveAssignedTasks(assignedTaskManager.getAssignTasks());
            patientManager.deletePatient(patientToBeDeleted.getId());
        } catch (DukeException e) {
            patientManager.deletePatient(patientToBeDeleted.getId());
        }
        storageManager.savePatients(patientManager.getPatientList());
        dukeUi.patientDeleted();
    }

    /**
     * It terminates the Dukepital.
     *
     * @return false.
     */
    @Override
    public boolean isExit() {
        return false;
    }
}
