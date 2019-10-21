package duke.command;

import duke.core.DukeException;
import duke.core.Ui;
import duke.patient.Patient;
import duke.patient.PatientManager;
import duke.storage.PatientStorage;
import duke.storage.PatientTaskStorage;
import duke.storage.TaskStorage;
import duke.relation.PatientTaskList;
import duke.task.TaskManager;

import java.util.ArrayList;

public class FindPatientCommand extends Command {

    private String patientInfo;
    private int id;

    /**
     * Constructor method for FindPatientCommand.
     * @param patientInfo Takes in patient information to be used to locate the desired patient.
     * @throws DukeException when unable to successfully parse a patient ID number.
     */
    public FindPatientCommand(String patientInfo) throws DukeException {

        this.patientInfo = patientInfo;

        if (patientInfo.charAt(0) == '#') {
            try {
                this.id = Integer.parseInt(patientInfo.substring(1));
            } catch (Exception e) {
                throw new DukeException("Failed to find ID.");
            }
        }
    }

    /**
     * .
     *
     * @param patientTask        .
     * @param tasks              .
     * @param patientManager     .
     * @param ui                 .
     * @param patientTaskStorage .
     * @param taskStorage        .
     * @param patientStorage     .
     * @throws DukeException .
     */
    @Override
    public void execute(PatientTaskList patientTask, TaskManager tasks, PatientManager patientManager,
                        Ui ui, PatientTaskStorage patientTaskStorage, TaskStorage taskStorage,
                        PatientStorage patientStorage) throws DukeException {
        char firstChar = patientInfo.charAt(0);
        if (id != 0) {
            Patient patient = patientManager.getPatient(id);
            ui.patientsFoundById(patient);
        } else {
            ArrayList<Patient> patientsWithSameName = patientManager.getPatientByName(patientInfo);
            ui.patientsFoundByName(patientsWithSameName, patientInfo);
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
