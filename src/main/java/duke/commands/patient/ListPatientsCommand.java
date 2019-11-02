package duke.commands.patient;

import duke.commands.Command;
import duke.exceptions.DukeException;
import duke.util.DukeUi;
import duke.models.patients.Patient;
import duke.models.patients.PatientManager;
import duke.models.assignedtasks.AssignedTaskManager;
import duke.storages.StorageManager;
import duke.models.tasks.TaskManager;

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
     * @param dukeUi                 .
     * @param storageManager .
     * @throws DukeException .
     */
    @Override
    public void execute(AssignedTaskManager patientTask, TaskManager tasks, PatientManager patientList,
                        DukeUi dukeUi, StorageManager storageManager) {

        ArrayList<Patient> list = patientList.getPatientList();
        dukeUi.listAllPatients(list);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
