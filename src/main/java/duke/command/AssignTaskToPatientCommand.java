package duke.command;

import duke.core.DukeException;
import duke.core.Ui;
import duke.patient.PatientManager;
import duke.storage.PatientStorage;
import duke.storage.PatientTaskStorage;
import duke.storage.TaskStorage;
import duke.relation.PatientTask;
import duke.relation.PatientTaskList;
import duke.task.TaskManager;

public class AssignTaskToPatientCommand extends Command {

    private PatientTask newPatientTask;

    public AssignTaskToPatientCommand(PatientTask patientTask) {
        super();
        this.newPatientTask = patientTask;
    }

    @Override
    public void execute(PatientTaskList patientTaskList, TaskManager tasksList, PatientManager patientList, Ui ui, PatientTaskStorage patientTaskStorage, TaskStorage taskStorage, PatientStorage patientStorage) throws DukeException {
        if (patientList.isExist(newPatientTask.getPatientId()) && tasksList.isExist(newPatientTask.getTaskID()))
        {
            patientTaskList.addPatientTask(newPatientTask);
            patientTaskStorage.save(patientTaskList.fullPatientTaskList());
            ui.patientTaskAssigned(newPatientTask, patientList.getPatient(newPatientTask.getPatientId()).getName(), tasksList.getTask(newPatientTask.getTaskID()).getDescription());
        }
        else
        {
            throw new DukeException("Either the patient or the task does not exist in our data record");
        }

    }

    @Override
    public boolean isExit() {
        return false;
    }
}
