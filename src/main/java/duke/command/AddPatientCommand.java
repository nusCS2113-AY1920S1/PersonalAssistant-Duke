package duke.command;

import duke.core.DukeException;
import duke.core.Ui;
import duke.patient.Patient;
import duke.patient.PatientManager;
import duke.relation.PatientTaskList;
import duke.storage.StorageManager;
import duke.task.TaskManager;

public class AddPatientCommand extends Command {

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
    public void execute(PatientTaskList patientTask, TaskManager tasks, PatientManager patientList, Ui ui,
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
