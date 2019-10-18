package duke.command;

import duke.core.DateTimeParser;
import duke.core.DukeException;
import duke.core.Ui;
import duke.patient.Patient;
import duke.patient.PatientManager;
import duke.relation.EventPatientTask;
import duke.relation.PatientTask;
import duke.relation.StandardPatientTask;
import duke.storage.PatientStorage;
import duke.storage.PatientTaskStorage;
import duke.storage.TaskStorage;
import duke.relation.PatientTaskList;
import duke.task.Task;
import duke.task.TaskManager;


import java.time.LocalDateTime;
import java.util.ArrayList;

public class DeletePatientTaskCommand extends Command {
    private int patientId;
    private int taskId;
    private String deletedPatientInfo;
    private String[] command;

    public DeletePatientTaskCommand(String[] deleteInfo) throws DukeException {

        char firstChar = deleteInfo[0].charAt(0);
        try{
            if (firstChar == '#'){
                this.patientId = Integer.parseInt(deleteInfo[0].replace("#","").trim());
                this.taskId = Integer.parseInt(deleteInfo[1]);
            } else {
                this.deletedPatientInfo = deleteInfo[0];
                this.taskId = Integer.parseInt(deleteInfo[1]);
            }
        }catch(Exception e) {
            throw new DukeException("Try to follow the format: delete patienttask #patientid taskuniqeid");
        }

    }

    @Override
    public void execute(PatientTaskList patientTaskList, TaskManager tasks, PatientManager patientManager, Ui ui, PatientTaskStorage patientTaskStorage, TaskStorage taskStorage, PatientStorage patientStorage) throws DukeException { ;
        if (patientId != 0) {
            try{
                Patient ToBeDeletedPatient = patientManager.getPatient(patientId);
                for (PatientTask patientTask: patientTaskList.getPatientTask(patientId)){
                    if (patientTask.getUid() == taskId){
                        patientTaskList.deletePatientTaskByUniqueId(taskId);
                        ui.patientTaskDeleted(patientTask, ToBeDeletedPatient);
                    }
                }
            }catch(DukeException e) {
                throw new DukeException("Task or patient is not found!" + e.getMessage());
            }
        } else {
            try{
                String patientName = this.deletedPatientInfo;
                ArrayList<Patient> patientsWithSameName = patientManager.getPatientByName(patientName);
                ArrayList<PatientTask> ToBeDeleted = patientTaskList.getPatientTask(patientsWithSameName.get(0).getID());
                for (PatientTask patientTask: ToBeDeleted){
                    if (patientTask.getUid() == taskId) {
                        patientTaskList.deletePatientTaskByUniqueId(taskId);
                        ui.patientTaskDeleted(patientTask, patientsWithSameName.get(0));
                    }
                }
            }catch(Exception e) {
                throw new DukeException("Task or patient is not found!");
            }
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}