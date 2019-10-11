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

import java.util.ArrayList;

public class ListPatientsCommand extends Command {

    public ListPatientsCommand() {
        super();
    }

    @Override
    public void execute(PatientTaskList patientTask, TaskList tasks, PatientList patientList, Ui ui, PatientTaskStorage patientTaskStorage, TaskStorage taskStorage, PatientStorage patientStorage) throws DukeException {
        ArrayList<Patient> list = patientList.getPatientList();
        ui.listAllPatients(list);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
