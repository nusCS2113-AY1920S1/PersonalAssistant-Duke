//@@kkeejjuunn

package duke.command;

import duke.core.DukeException;
import duke.core.Ui;
import duke.patient.Patient;
import duke.patient.PatientManager;
import duke.relation.PatientTaskList;
import duke.storage.StorageManager;
import duke.task.TaskManager;

public class UpdatePatientCommand implements Command {

    private String[] command;

    /**
     * .
     *
     * @param command .
     */
    public UpdatePatientCommand(String[] command) {
        this.command = command;
    }

    /**
     * .
     *
     * @param patientTask        .
     * @param tasks              .
     * @param patientManager     .
     * @param ui                 .
     * @param storageManager .
     * @throws DukeException .
     */
    @Override
    public void execute(PatientTaskList patientTask, TaskManager tasks, PatientManager patientManager,
                        Ui ui, StorageManager storageManager) throws DukeException {
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
                } else {
                    throw new DukeException("You can only update 'Name', 'NRIC', or 'Room' of the patient");
                }

                storageManager.savePatients(patientManager.getPatientList());
                ui.showUpdatedSuccessfully();
                ui.showPatientInfo(patientToBeUpdated);
            } catch (Exception e) {
                throw new DukeException(
                        "Please follow the format 'update patient :#<id> :<Name/NRIC/Room> :<new information>'.");
            }
        } else {
            throw new DukeException(
                    "Please follow the format 'update patient :#<id> :<Name/NRIC/Room> :<new information>'.");
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
