package duke.command;

import duke.core.DukeException;
import duke.core.Ui;
import duke.patient.Patient;
import duke.patient.PatientManager;
import duke.storage.PatientStorage;
import duke.storage.TaskStorage;
import duke.task.TaskManager;

public class AddPatientCommand extends Command {

    private Patient newPatient;
    public AddPatientCommand(Patient newPatient) {
        super();
        this.newPatient = newPatient;
    }

    @Override
    public void execute(TaskManager tasks, PatientManager patientManager, Ui ui, TaskStorage taskStorage, PatientStorage patientStorage) throws DukeException {
        patientManager.addPatient(newPatient);
        patientStorage.save(patientManager.getPatientList());
        ui.patientAdded(newPatient);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
