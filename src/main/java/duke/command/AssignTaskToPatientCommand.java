package duke.command;

import duke.core.DukeException;
import duke.core.Ui;
import duke.patient.PatientManager;
import duke.relation.EventPatientTask;
import duke.relation.StandardPatientTask;
import duke.storage.PatientStorage;
import duke.storage.PatientTaskStorage;
import duke.storage.TaskStorage;
import duke.relation.PatientTask;
import duke.relation.PatientTaskList;
import duke.task.TaskManager;

public class AssignTaskToPatientCommand extends Command {

    private String command;
    private PatientTask newPatientTask;

    public AssignTaskToPatientCommand(String cmd) {
        super();
        this.command = cmd;
    }

    @Override
    public void execute(PatientTaskList patientTaskList, TaskManager tasksList, PatientManager patientList, Ui ui, PatientTaskStorage patientTaskStorage, TaskStorage taskStorage, PatientStorage patientStorage) throws DukeException {
        this.newPatientTask = finalPatientTask(command);
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

    public PatientTask finalPatientTask(String cmd) throws DukeException {
        String[] tempCommand = command.split("\\s+",4 );
        if (tempCommand[0].equals("S")) {
            String type = tempCommand[0];
            int patientId = Integer.parseInt(tempCommand[1]);
            int taskId = Integer.parseInt(tempCommand[2]);
            String deadline = tempCommand[3];
            StandardPatientTask sPatientTask = new StandardPatientTask(patientId, taskId, deadline, type);
            return sPatientTask;
        } else if (tempCommand[0].equals("E")) {
            String type = tempCommand[0];
            int patientId = Integer.parseInt(tempCommand[1]);
            int taskId = Integer.parseInt(tempCommand[2]);
            String sTime = tempCommand[3].split(" /to ", 2)[0];
            String eTime = tempCommand[3].split(" /to ", 2)[1];
            EventPatientTask ePatientTask = new EventPatientTask(patientId, taskId, sTime, eTime, type);
            return  ePatientTask;
        }
        else{
            throw new DukeException("Parsing failed! Please ensure the format you have entered is correct!");
        }
    }
    @Override
    public boolean isExit() {
        return false;
    }
}
