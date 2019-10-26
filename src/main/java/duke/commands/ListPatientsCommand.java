package duke.commands;

import duke.exceptions.DukeException;
import duke.util.Ui;
import duke.models.patients.Patient;
import duke.models.patients.PatientManager;
import duke.models.assignedPatientTasks.AssignedTaskManager;
import duke.storages.StorageManager;
import duke.models.patientTasks.TaskManager;

import java.util.ArrayList;

public class ListPatientsCommand implements Command {

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
    public void execute(AssignedTaskManager patientTask, TaskManager tasks, PatientManager patientList,
                        Ui ui, StorageManager storageManager) {

        ArrayList<Patient> list = patientList.getPatientList();
        ui.listAllPatients(list);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
