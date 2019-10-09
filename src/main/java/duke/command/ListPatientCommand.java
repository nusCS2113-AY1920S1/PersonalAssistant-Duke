package duke.command;

import duke.core.DukeException;
import duke.core.Ui;
import duke.patient.Patient;
import duke.patient.PatientList;
import duke.storage.PatientStorage;
import duke.storage.TaskStorage;
import duke.task.TaskList;

import java.util.ArrayList;

public class ListPatientCommand extends Command {

    public ListPatientCommand() {
        super();
    }

    @Override
    public void execute(TaskList tasks, PatientList patientList, Ui ui, TaskStorage taskStorage, PatientStorage patientStorage) throws DukeException {
        ArrayList<Patient> list = patientList.getPatientList();
        ui.listAllPatients(list);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
