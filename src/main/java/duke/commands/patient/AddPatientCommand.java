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
     * It extracts the patient information.
     *
     * @param patientInfo    contains the patient information to be added.
     * @throws DukeException if there ia any information is missing.
     */
    public AddPatientCommand(String[] patientInfo) throws DukeException {
        super();
        try {
            this.newPatient = new Patient(patientInfo[0], patientInfo[1], patientInfo[2], patientInfo[3]);

        } catch (Exception e) {
            throw new DukeException(AddPatientCommand.class,
                    "Please follow the format 'add patient :<Name> :<NRIC> :<Room> :<remark>'. ");
        }
    }

    /**
     * It adds a new patient to Dukepital.
     *
     * @param assignedTaskManager contains the information between all the tasks and patients.
     * @param taskManager         contains information of all the tasks.
     * @param patientManager      contains information of all the patients.
     * @param dukeUi              interacts with user.
     * @param storageManager      save the changes in csv file.
     * @throws DukeException      if the patient is not added successfully.
     */
    @Override
    public void execute(AssignedTaskManager assignedTaskManager, TaskManager taskManager, PatientManager patientManager,
                        DukeUi dukeUi, StorageManager storageManager) throws DukeException {

        try {
            patientManager.addPatient(newPatient);
            storageManager.savePatients(patientManager.getPatientList());
            dukeUi.patientAdded(newPatient);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * It terminates the Dukepital.
     *
     * @return false.
     */
    @Override
    public boolean isExit() {
        return false;
    }


}
