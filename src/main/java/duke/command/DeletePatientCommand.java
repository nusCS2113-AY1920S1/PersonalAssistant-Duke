package duke.command;

import duke.core.DukeException;
import duke.core.Ui;
import duke.patient.Patient;
import duke.patient.PatientManager;
import duke.relation.PatientTask;
import duke.statistic.Counter;
import duke.storage.CounterStorage;
import duke.storage.PatientStorage;
import duke.storage.PatientTaskStorage;
import duke.storage.TaskStorage;
import duke.relation.PatientTaskList;
import duke.task.Task;
import duke.task.TaskManager;


import java.util.ArrayList;

public class DeletePatientCommand extends Command {
    private int id;
    private String deletedPatientInfo;

    /**
     * It checks whether user is deleting a patient by id.
     * It extracts the id of the patient to be deleted.
     *
     * @param deletedPatientInfo it contains the information of the patient to be deleted
     * @throws DukeException it shows user the correct format to delete a patient by id
     */
    public DeletePatientCommand(String deletedPatientInfo) throws DukeException {

        this.deletedPatientInfo = deletedPatientInfo;
        char firstChar = deletedPatientInfo.charAt(0);
        if (firstChar == '#') {
            try {
                this.id = Integer.parseInt(deletedPatientInfo.substring(1));
            } catch (Exception e) {
                throw new DukeException("Please follow format 'delete patient #<id>'. ");
            }
        }
    }

    /**
     * .
     *
     * @param patientTaskList        .
     * @param taskManager              .
     * @param patientManager     .
     * @param ui                 .
     * @param patientTaskStorage .
     * @param taskStorage        .
     * @param patientStorage     .
     * @throws DukeException .
     */
    @Override
    public void execute(PatientTaskList patientTaskList, TaskManager taskManager, PatientManager patientManager,
                        Ui ui, PatientTaskStorage patientTaskStorage, TaskStorage taskStorage,
                        PatientStorage patientStorage) throws DukeException {

        boolean toDelete;
        ArrayList<PatientTask> patientTask = new ArrayList<>();
        Patient patientToBeDeleted = null;

        if (id != 0) {
            patientToBeDeleted = patientManager.getPatient(id);
            ui.showPatientInfo(patientToBeDeleted);
        } else {
            ArrayList<Patient> patientsWithSameName = patientManager.getPatientByName(deletedPatientInfo);
            ui.patientsFoundByName(patientsWithSameName, deletedPatientInfo);
            if (patientsWithSameName.size() >= 1) {
                int numberChosen = ui.choosePatientToDelete(patientsWithSameName.size());
                if (numberChosen >= 1) {
                    patientToBeDeleted = patientsWithSameName.get(numberChosen - 1);
                    ui.showPatientInfo(patientToBeDeleted);
                }
            }
        }
        try {
            patientTask = patientTaskList.getPatientTask(patientToBeDeleted.getID());
            ArrayList<Task> tempTask = new ArrayList<>();
            for (PatientTask temppatientTask : patientTask) {
                tempTask.add(taskManager.getTask(temppatientTask.getTaskID()));
            }
            ui.patientTaskFound(patientToBeDeleted, patientTask, tempTask);
            toDelete = ui.confirmPatientToBeDeleted(patientToBeDeleted, true);
        } catch (Exception e) {
            toDelete = ui.confirmPatientToBeDeleted(patientToBeDeleted,false);
        }
        if (toDelete) {
            patientManager.deletePatient(patientToBeDeleted.getID());
            patientTaskList.deleteEntirePatientTask(patientToBeDeleted.getID());
            ui.patientDeleted();
            patientStorage.save(patientManager.getPatientList());
            patientTaskStorage.save(patientTaskList.fullPatientTaskList());
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
