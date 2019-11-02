//@@author kkeejjuunn

package duke.commands.patient;

import duke.commands.Command;
import duke.exceptions.DukeException;
import duke.models.tasks.TaskManager;
import duke.util.DukeUi;
import duke.models.patients.Patient;
import duke.models.patients.PatientManager;
import duke.models.assignedtasks.AssignedTaskManager;
import duke.storages.StorageManager;

public class AddPatientCommand implements Command {

    private Patient newPatient;

    /**
     * .
     *
     * @param patientInfo .
     * @throws DukeException .
     */
    public AddPatientCommand(String[] patientInfo) throws DukeException {
        super();
        try {
            this.newPatient = new Patient(patientInfo[0], patientInfo[1], patientInfo[2], patientInfo[3]);

        } catch (Exception e) {
            throw new DukeException(AddPatientCommand.class,
                    "Please follow the format 'add patient <name> <NRIC> <Room> <remark>'. ");
        }
    }

    @Override
    public void execute(AssignedTaskManager patientTask, TaskManager tasks, PatientManager patientList, DukeUi dukeUi,
                        StorageManager storageManager) throws DukeException {

        patientList.addPatient(newPatient);
        storageManager.savePatients(patientList.getPatientList());
        dukeUi.patientAdded(newPatient);
    }

    @Override
    public boolean isExit() {
        return false;
    }


}
