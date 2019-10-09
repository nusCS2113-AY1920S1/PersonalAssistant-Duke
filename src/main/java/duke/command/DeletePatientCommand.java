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

    public DeletePatientCommand(int id) {
        this.id = id;
    }

    @Override
    public void execute(TaskList tasks, PatientList patientList, Ui ui, TaskStorage taskStorage, PatientStorage patientStorage) throws DukeException {
        Patient patientToBeDeleted = patientList.getPatient(id);
        patientList.deletePatient(id);
        patientStorage.save(patientList.getPatientList());
        ui.patientDeleted(patientToBeDeleted);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
