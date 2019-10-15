package duke.command;

import duke.core.DukeException;
import duke.core.Ui;
import duke.patient.Patient;
import duke.patient.PatientManager;
import duke.storage.PatientStorage;
import duke.storage.PatientTaskStorage;
import duke.storage.TaskStorage;
import duke.relation.PatientTaskList;
import duke.task.TaskManager;


import java.util.ArrayList;

public class DeletePatientCommand extends Command {
    private int id;
    private String deletedPatientInfo;

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

    @Override
    public void execute(PatientTaskList patientTask, TaskManager tasks, PatientManager patientManager, Ui ui, PatientTaskStorage patientTaskStorage, TaskStorage taskStorage, PatientStorage patientStorage) throws DukeException { ;
        if (id != 0) {
            Patient patientToBeDeleted = patientManager.getPatient(id);
            boolean toDelete = ui.confirmPatientToBeDeleted(patientToBeDeleted);
            if (toDelete){
                patientManager.deletePatient(id);
                ui.patientDeleted();
                patientStorage.save(patientManager.getPatientList());
            }
        } else {
            ArrayList<Patient> patientsWithSameName = patientManager.getPatientByName(deletedPatientInfo);
            ui.patientsFoundByName(patientsWithSameName, deletedPatientInfo);
            if (patientsWithSameName.size() >= 1) {
                int numberChosen = ui.choosePatientToDelete(patientsWithSameName.size());
                if (numberChosen >= 1){
                    boolean toDelete = ui.confirmPatientToBeDeleted(patientsWithSameName.get(numberChosen-1));
                    if (toDelete){
                        patientManager.deletePatient(patientsWithSameName.get(numberChosen-1).getID());
                        ui.patientDeleted();
                        patientStorage.save(patientManager.getPatientList());
                    }
                }
            }
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
