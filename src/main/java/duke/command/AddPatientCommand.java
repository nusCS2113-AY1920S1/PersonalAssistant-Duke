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

public class AddPatientCommand extends Command {
    private String command;
    public AddPatientCommand(String command) {
        super();
        this.command = command;
    }

    @Override
    public void execute(PatientTaskList patientTask, TaskManager tasks, PatientManager patientList, Ui ui, PatientTaskStorage patientTaskStorage, TaskStorage taskStorage, PatientStorage patientStorage) throws DukeException {
        Patient newPatient;
        try {
            String commandArr[] = command.split("\\s+", 4);
            newPatient = new Patient(commandArr[0], commandArr[1], commandArr[2], commandArr[3]);
        } catch (Exception e) {
            throw new DukeException("Please follow the format 'add patient <name> <NRIC> <Room> <remark>'. ");
        }
        patientList.addPatient(newPatient);
        patientStorage.save(patientList.getPatientList());
        ui.patientAdded(newPatient);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
