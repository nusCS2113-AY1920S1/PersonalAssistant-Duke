package duke.command;

import duke.core.DukeException;
import duke.core.Ui;
import duke.patient.Patient;
import duke.patient.PatientList;
import duke.storage.PatientStorage;
import duke.storage.TaskStorage;
import duke.task.TaskList;

public class UpdateCommand extends Command {

    private int Id;
    private String targetInfo;
    private String updatedValue;

    public UpdateCommand(int Id , String targetInfo , String updatedValue) {
        super();
        this.Id = Id;
        this.targetInfo = targetInfo;
        this.updatedValue = updatedValue;
    }

    @Override
    public void execute(TaskList tasks, PatientList patientList, Ui ui, TaskStorage taskStorage, PatientStorage patientStorage) throws DukeException {
        Patient targetPatient = patientList.getPatient(Id);
            if (targetInfo.equals("name")) {
                targetPatient.setName(updatedValue);
            } else if (targetInfo.equals("nric")) {
                targetPatient.setNric(updatedValue);
            } else if (targetInfo.equals("room")) {
                targetPatient.setRoom(updatedValue);
            } else {
                throw new DukeException("No such Patient information existed. Please Enter a valid Patient information");
            }
            patientStorage.save(patientList.getPatientList());
            ui.showUpdateStatus(targetPatient , targetInfo);

    }

    @Override
    public boolean isExit() {
        return false;
    }
}
