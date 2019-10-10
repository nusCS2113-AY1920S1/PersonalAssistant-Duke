package duke.command;

import duke.core.DukeException;
import duke.core.Ui;
import duke.patient.Patient;
import duke.patient.PatientList;
import duke.storage.PatientStorage;
import duke.storage.TaskStorage;
import duke.task.TaskList;

import java.util.ArrayList;

public class DeletePatientCommand extends Command {
    private int id;
    private String command;

    public DeletePatientCommand(String command) {
        this.command = command;
    }

    @Override
    public void execute(TaskList tasks, PatientList patientList, Ui ui, TaskStorage taskStorage, PatientStorage patientStorage) throws DukeException {
        char firstChar = command.charAt(0);
        if (firstChar == '#') {
            int id;
            try{
                id = Integer.parseInt(command.substring(1, command.length()));
            }catch(Exception e){
                throw new DukeException("Please follow the format 'delete patient #<id>'.");
            }

            Patient patientToBeDeleted = patientList.getPatient(id);
            boolean toDelete = ui.confirmPatientToBeDeleted(patientToBeDeleted);
            if (toDelete){
                patientList.deletePatient(id);
                ui.patientDeleted();
                patientStorage.save(patientList.getPatientList());
            }
        } else {
            ArrayList<Patient> patientsWithSameName = patientList.getPatientByName(command);
            ui.patientsFoundByName(patientsWithSameName, command);
            if (patientsWithSameName.size() >= 1) {
                int numberChosen = ui.choosePatientToDelete(patientsWithSameName.size());
                if (numberChosen >= 1){
                    boolean toDelete = ui.confirmPatientToBeDeleted(patientsWithSameName.get(numberChosen-1));
                    if (toDelete){
                        patientList.deletePatient(patientsWithSameName.get(numberChosen-1).getID());
                        ui.patientDeleted();
                        patientStorage.save(patientList.getPatientList());
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
