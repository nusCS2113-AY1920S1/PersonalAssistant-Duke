package duke.command;

import duke.core.DukeException;
import duke.core.Ui;
import duke.patient.Patient;
import duke.patient.PatientList;
import duke.storage.PatientStorage;
import duke.storage.TaskStorage;
import duke.task.TaskList;

import java.util.ArrayList;

public class FindPatientCommand extends Command {

    private String command;
    public FindPatientCommand(String command){
        this.command = command;
    }

    @Override
    public void execute(TaskList tasks, PatientList patientList, Ui ui, TaskStorage taskStorage, PatientStorage patientStorage) throws DukeException {
        char firstChar = command.charAt(0);
        if (firstChar == '#'){
            int id = Integer.parseInt(command.substring(1, command.length()));
            Patient patient = patientList.getPatient(id);
            ui.patientsFoundById(patient);
        }
        else{
            ArrayList<Patient> patientsWithSameName = patientList.getPatientByName(command);
            ui.patientsFoundByName(patientsWithSameName, command);
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
