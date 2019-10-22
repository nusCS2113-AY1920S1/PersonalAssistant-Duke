package duke.command;

import duke.core.DukeException;
import duke.core.Ui;
import duke.patient.Patient;
import duke.patient.PatientManager;
import duke.relation.PatientTaskList;
import duke.storage.StorageManager;
import duke.task.TaskManager;

import java.util.ArrayList;

public class ListPatientsCommand extends Command {

    public ListPatientsCommand() {
        super();
    }

    /**
     * .
     *
     * @param patientTask        .
     * @param tasks              .
     * @param patientList        .
     * @param ui                 .
     * @param storageManager .
     * @throws DukeException .
     */
    @Override
    public void execute(PatientTaskList patientTask, TaskManager tasks, PatientManager patientList,
                        Ui ui, StorageManager storageManager) {

        ArrayList<Patient> list = patientList.getPatientList();
        ui.listAllPatients(list);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
