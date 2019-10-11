package duke.command;

import duke.core.DukeException;
import duke.core.Ui;
import duke.patient.Patient;
import duke.patient.PatientManager;
import duke.storage.PatientStorage;
import duke.storage.TaskStorage;
import duke.task.TaskManager;

import java.util.ArrayList;

public class FindPatientCommand extends Command {

    private String command;
    public FindPatientCommand(String command){
        this.command = command;
    }

    @Override
    public void execute(TaskManager tasks, PatientManager patientManager, Ui ui, TaskStorage taskStorage, PatientStorage patientStorage) throws DukeException {
        char firstChar = command.charAt(0);
        if (firstChar == '#'){
            int id;
            try {
                id = Integer.parseInt(command.substring(1, command.length()));
            }catch(Exception e) {
                throw new DukeException("Please follow the format 'find patient #<id>' or 'find patient <name>'.");
            }
            Patient patient = patientManager.getPatient(id);
            ui.patientsFoundById(patient);
        }
        else{
            ArrayList<Patient> patientsWithSameName = patientManager.getPatientByName(command);
            ui.patientsFoundByName(patientsWithSameName, command);
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
