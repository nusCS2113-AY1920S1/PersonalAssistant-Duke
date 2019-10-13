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

public class UpdatePatientCommand extends Command {

    private int Id;
    private String targetInfo;
    private String updatedValue;

    public UpdatePatientCommand(int Id , String targetInfo , String updatedValue) {
        super();
        this.Id = Id;
        this.targetInfo = targetInfo;
        this.updatedValue = updatedValue;
    }

    @Override
    public void execute(PatientTaskList patientTask, TaskManager tasks, PatientManager patientManager, Ui ui, PatientTaskStorage patientTaskStorage, TaskStorage taskStorage, PatientStorage patientStorage) throws DukeException {
        Patient targetPatient = patientManager.getPatient(Id);
            if (targetInfo.equals("name")) {
                targetPatient.setName(updatedValue);
            } else if (targetInfo.equals("nric")) {
                targetPatient.setNric(updatedValue);
            } else if (targetInfo.equals("room")) {
                targetPatient.setRoom(updatedValue);
            } else {
                throw new DukeException("No such Patient information existed. Please Enter a valid Patient information");
            }
            patientStorage.save(patientManager.getPatientList());
            ui.showUpdateStatus(targetPatient , targetInfo);

    }

    @Override
    public boolean isExit() {
        return false;
    }
}
