package duke.command;

import duke.core.DukeException;
import duke.core.Ui;
import duke.patient.Patient;
import duke.patient.PatientList;
import duke.storage.PatientStorage;
import duke.storage.PatientTaskStorage;
import duke.storage.TaskStorage;
import duke.relation.PatientTaskList;
import duke.task.TaskList;

public class AddPatientCommand extends Command {

    private Patient newPatient;
    public AddPatientCommand(Patient newPatient) {
        super();
        this.newPatient = newPatient;
    }

    @Override
    public void execute(PatientTaskList patientTask, TaskList tasks, PatientList patientList, Ui ui, PatientTaskStorage patientTaskStorage,TaskStorage taskStorage, PatientStorage patientStorage) throws DukeException {
        patientList.addPatient(newPatient);
        patientStorage.save(patientList.getPatientList());
        ui.patientAdded(newPatient);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
