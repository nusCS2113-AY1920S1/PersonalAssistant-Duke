package duke.command;

import duke.core.DukeException;
import duke.core.Ui;
import duke.patient.Patient;
import duke.patient.PatientManager;
import duke.storage.PatientStorage;
import duke.storage.TaskStorage;
import duke.task.TaskManager;

import java.util.ArrayList;

public class ListPatientsCommand extends Command {

    public ListPatientsCommand() {
        super();
    }

    @Override
    public void execute(TaskManager tasks, PatientManager patientManager, Ui ui, TaskStorage taskStorage, PatientStorage patientStorage) throws DukeException {
        ArrayList<Patient> list = patientManager.getPatientList();
        ui.listAllPatients(list);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
