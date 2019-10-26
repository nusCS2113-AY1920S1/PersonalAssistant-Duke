package duke.commands;

import duke.exceptions.DukeException;
import duke.util.Ui;
import duke.models.patients.Patient;
import duke.models.patients.PatientManager;
import duke.models.assignedPatientTasks.AssignedTaskManager;
import duke.storages.StorageManager;
import duke.models.patientTasks.TaskManager;

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
            throw new DukeException("Please follow the format 'add patient <name> <NRIC> <Room> <remark>'. ");
        }
    }

    @Override
    public void execute(AssignedTaskManager patientTask, TaskManager tasks, PatientManager patientList, Ui ui,
                        StorageManager storageManager) throws DukeException {

        patientList.addPatient(newPatient);
        storageManager.savePatients(patientList.getPatientList());
        ui.patientAdded(newPatient);
    }

    @Override
    public boolean isExit() {
        return false;
    }


}
