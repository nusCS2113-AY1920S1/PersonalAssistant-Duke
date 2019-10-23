package duke.command;

import duke.core.DukeException;
import duke.core.Ui;
import duke.patient.Patient;
import duke.patient.PatientManager;
import duke.relation.PatientTask;
import duke.relation.PatientTaskList;
import duke.task.Task;
import duke.task.TaskManager;
import duke.storage.StorageManager;

import java.util.ArrayList;

public class DeletePatientCommand extends Command {
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
     * It checks whether user is deleting by id or name.
     * It retrieves patient based on the id extracted.
     *
     * @param deletedPatientInfo contains the delete patient command.
     * @param ui allows user to choose the patient to delete.
     * @param patientManager retrieves patient based on patient id.
     * @return patient to be deleted.
     * @throws DukeException if no matched patient found.
     */
    public Patient getPatientByDeletePatientCommand(String deletedPatientInfo, Ui ui,
                                                    PatientManager patientManager) throws DukeException {
        char firstChar = deletedPatientInfo.charAt(0);
        Patient patient = null;
        if (firstChar == '#') {
            int id;
            try {
                id = Integer.parseInt(deletedPatientInfo.substring(1));
            } catch (Exception e) {
                throw new DukeException("Please follow format 'delete patient #<id>'. ");
            }
            try {
                patient = patientManager.getPatient(id);
            } catch (Exception e) {
                throw new DukeException("The patient id does not exist. ");
            }
        } else {
            ArrayList<Patient> patientsWithSameName = patientManager.getPatientByName(deletedPatientInfo);
            if (patientsWithSameName.size() >= 1) {
                ui.patientsFoundByName(patientsWithSameName, deletedPatientInfo);
                int numberChosen = ui.choosePatientToDelete(patientsWithSameName.size());
                if (numberChosen >= 1) {
                    patient = patientsWithSameName.get(numberChosen - 1);
                }
            } else {
                throw new DukeException("There is no patients matched this name.");
            }
        }
        return patient;
    }

    /**
     * It deletes the patient returned from getPatientByDeletePatientCommand.
     * It checks whether this patient is assigned to any tasks.
     * It deletes the relationship between this patient and any tasks.
     *
     * @param patientTaskList contains the information between all the tasks and patients.
     * @param taskManager contains information of all the tasks.
     * @param patientManager contains information of all the patients.
     * @param ui interacts with user.
     * @param storageManager save the changes in csv file.
     * @throws DukeException if there is error deleting the patient.
     */
    @Override
    public void execute(PatientTaskList patientTaskList, TaskManager taskManager, PatientManager patientManager,
                        Ui ui, StorageManager storageManager) throws DukeException {
        try {
            patientToBeDeleted = getPatientByDeletePatientCommand(deletedPatientInfo, ui, patientManager);
        } catch (Exception e) {
            ui.showError(e.getMessage());
        }

        boolean toDelete;
        ui.showPatientInfo(patientToBeDeleted);
        try {
            ArrayList<PatientTask> patientTask = patientTaskList.getPatientTask(patientToBeDeleted.getID());
            ArrayList<Task> tempTask = new ArrayList<>();
            for (PatientTask tempPatientTask : patientTask) {
                tempTask.add(taskManager.getTask(tempPatientTask.getTaskID()));
            }
            ui.patientTaskFound(patientToBeDeleted, patientTask, tempTask);
            toDelete = ui.confirmPatientToBeDeleted(patientToBeDeleted, true);
            if (toDelete) {
                patientTaskList.deleteAllTasksBelongToThePatient(patientToBeDeleted.getID());
                storageManager.saveAssignedTasks(patientTaskList.fullPatientTaskList());
            }
        } catch (Exception e) {
            toDelete = ui.confirmPatientToBeDeleted(patientToBeDeleted,false);
        }
        if (toDelete) {
            patientManager.deletePatient(patientToBeDeleted.getID());
            storageManager.savePatients(patientManager.getPatientList());
            ui.patientDeleted();

        }
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
