package duke.command;

import duke.core.DukeException;
import duke.core.Ui;
import duke.patient.Patient;
import duke.patient.PatientManager;
import duke.relation.PatientTaskList;
import duke.storage.StorageManager;
import duke.task.TaskManager;

import java.util.ArrayList;

public class FindPatientCommand extends Command {

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
     * @param ui                 .
     * @param storageManager .
     * @throws DukeException .
     */
    @Override
    public void execute(PatientTaskList patientTask, TaskManager tasks, PatientManager patientManager,
                        Ui ui, StorageManager storageManager) throws DukeException {
        char firstChar = command.charAt(0);
        if (firstChar == '#') {
            int id;
            try {
                id = Integer.parseInt(command.substring(1, command.length()));
            } catch (Exception e) {
                throw new DukeException("Please follow the format 'find patient #<id>' or 'find patient <name>'.");
            }
            Patient patient = patientManager.getPatient(id);
            ui.patientsFoundById(patient);
        } else {
            ArrayList<Patient> patientsWithSameName = patientManager.getPatientByName(command);
            ui.patientsFoundByName(patientsWithSameName, command);
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