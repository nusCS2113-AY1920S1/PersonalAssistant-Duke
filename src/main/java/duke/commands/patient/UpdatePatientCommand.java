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

public class UpdatePatientCommand implements Command {

    private String[] command;

    public UpdatePatientCommand(String[] command) {
        this.command = command;
    }

    /**
     * It updates a new patient in Dukepital.
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
        char firstChar = command[0].charAt(0);
        if (firstChar == '#') {
            int id;
            try {
                id = Integer.parseInt(command[0].substring(1));
                Patient patientToBeUpdated = patientManager.getPatient(id);
                if (command[1].toLowerCase().equals("name")) {
                    patientToBeUpdated.setName(command[2]);
                } else if (command[1].toLowerCase().equals("nric")) {
                    patientToBeUpdated.setNric(command[2]);
                } else if (command[1].toLowerCase().equals("room")) {
                    patientToBeUpdated.setRoom(command[2]);
                } else if (command[1].toLowerCase().equals("remark")) {
                    patientToBeUpdated.setRemark(command[2]);
                } else {
                    throw new DukeException(UpdatePatientCommand.class,
                            "You can only update 'Name', 'NRIC', 'Room', or 'Remark' of the patient");
                }

                storageManager.savePatients(patientManager.getPatientList());
                dukeUi.showUpdatedSuccessfully();
                dukeUi.showPatientInfo(patientToBeUpdated);
            } catch (Exception e) {
                throw new DukeException(UpdatePatientCommand.class,
                        "Please follow the format 'update patient :#<id> :<Name/NRIC/Room/Remark> "
                                + ":<new information>'.");
            }
        } else {
            throw new DukeException(UpdatePatientCommand.class,
                    "Please follow the format 'update patient :#<id> :<Name/NRIC/Room/Remark> :<new information>'.");
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
