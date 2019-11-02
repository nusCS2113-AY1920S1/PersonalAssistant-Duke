//@@author kkeejjuunn

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

public class FindPatientCommand implements Command {

    private String command;

    public FindPatientCommand(String command) {
        this.command = command;
    }

    /**
     * .
     *
     * @param patientTask        .
     * @param tasks              .
     * @param patientManager     .
     * @param dukeUi                 .
     * @param storageManager .
     * @throws DukeException .
     */
    @Override
    public void execute(AssignedTaskManager patientTask, TaskManager tasks, PatientManager patientManager,
                        DukeUi dukeUi, StorageManager storageManager) throws DukeException {
        char firstChar = command.charAt(0);
        if (firstChar == '#') {
            int id;
            try {
                id = Integer.parseInt(command.substring(1, command.length()));
            } catch (Exception e) {
                throw new DukeException(FindPatientCommand.class, "The patient id is invalid.");
            }
            try {
                Patient patient = patientManager.getPatient(id);
                dukeUi.patientsFoundById(patient);
            } catch (Exception e) {
                throw new DukeException(FindPatientCommand.class, "The patient id does not exist.");
            }
        } else {
            ArrayList<Patient> patientsWithSameName = patientManager.getPatientByName(command);
            dukeUi.patientsFoundByName(patientsWithSameName, command);
        }
    }

    /**
     * .
     *
     * @return .
     */
    @Override
    public boolean isExit() {
        return false;
    }
}