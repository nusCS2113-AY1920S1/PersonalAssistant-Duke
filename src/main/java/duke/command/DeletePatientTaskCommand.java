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
    private String deletedTaskInfo;
    private String deletedPatientInfo;
    private LocalDateTime from;
    private LocalDateTime to;
    private LocalDateTime deadline;
    private String command;

    public DeletePatientTaskCommand(String deleteInfo) throws DukeException {

        this.command = deleteInfo;
        String[] tempCommand0 = command.split("\\s+", 2);
        char firstChar = tempCommand0[1].charAt(0);
        try{
            if (firstChar == '#' && tempCommand0[0].equals("E"))
            {
                String[] tempCommand1 = tempCommand0[1].split("\\s+", 2);
                this.patientId = Integer.parseInt(tempCommand1[0].substring(1));
                String[] tempCommand2 = tempCommand1[1].split(" from ", 2);
                this.taskId = Integer.parseInt(tempCommand2[0]);
                String[] tempCommand3 = tempCommand2[1].split(" to ", 2);
                this.from = DateTimeParser.convertToLocalDateTime(tempCommand3[0]);
                this.to = DateTimeParser.convertToLocalDateTime(tempCommand3[1]);
            }
            else if (firstChar == '#' && tempCommand0[0].equals("S")){
                String[] tempCommand1 = tempCommand0[1].split("\\s+", 2);
                this.patientId = Integer.parseInt(tempCommand1[0].substring(1));
                String[] tempCommand2 = tempCommand1[1].split(" deadline ", 2);
                this.taskId = Integer.parseInt(tempCommand2[0]);
                this.deadline = DateTimeParser.convertToLocalDateTime(tempCommand2[1]);
            }
            else {
                if (tempCommand0[0].equals("E")) {
                    String[] tempCommand1 = tempCommand0[1].split("\\s+", 2);
                    this.deletedPatientInfo = tempCommand1[0];
                    String[] tempCommand2 = tempCommand1[1].split(" from ", 2);
                    this.deletedTaskInfo = tempCommand2[0];
                    String[] tempCommand3 = tempCommand2[1].split(" to ", 2);
                    this.from = DateTimeParser.convertToLocalDateTime(tempCommand3[0]);
                    this.to = DateTimeParser.convertToLocalDateTime(tempCommand3[1]);
                } else if (tempCommand0[0].equals("S")) {
                    String[] tempCommand1 = tempCommand0[1].split("\\s+", 2);
                    this.deletedPatientInfo = tempCommand1[0];
                    String[] tempCommand2 = tempCommand1[1].split(" deadline ", 2);
                    this.deletedTaskInfo = tempCommand2[0];
                    this.deadline = DateTimeParser.convertToLocalDateTime(tempCommand2[1]);
                }
            }
        }catch(Exception e) {
            throw new DukeException("Try to follow the format: delete patienttask E/S #<patientId>/PatientName <TaskID>/TaskName from <starttime> to <endtime>/ deadline <deadlinetime>");

        }

    }

    @Override
    public void execute(PatientTaskList patientTask, TaskManager tasks, PatientManager patientManager, Ui ui, PatientTaskStorage patientTaskStorage, TaskStorage taskStorage, PatientStorage patientStorage) throws DukeException { ;
        if (patientId != 0) {
            try{
                ArrayList<PatientTask> ToBeDeleted = patientTask.getPatientTask(patientId);
                Patient ToBeDeletedPatient = patientManager.getPatient(patientId);
                Task ToBeDeletedTask = tasks.getTask(taskId);
                for (PatientTask patientTask1: ToBeDeleted){
                    if (patientTask1 instanceof EventPatientTask && patientTask1.getTaskID().equals(taskId) && ((EventPatientTask) patientTask1).getStartTime().equals(this.from) && ((EventPatientTask) patientTask1).getEndTime().equals(this.to)) {
                        patientTask.deletePatientTask(patientId,taskId,from,to);
                        ui.patientTaskDeleted(patientTask1, ToBeDeletedPatient, ToBeDeletedTask);
                    }
                    else if (patientTask1 instanceof StandardPatientTask && patientTask1.getTaskID().equals(taskId) && ((StandardPatientTask) patientTask1).getDeadline().equals(this.deadline)){
                        patientTask.deletePatientTask(patientId,taskId,deadline);
                        ui.patientTaskDeleted(patientTask1, ToBeDeletedPatient, ToBeDeletedTask);
                    }
                }
            }catch(Exception e) {
                e.printStackTrace();
                throw new DukeException("Task or patient is not found!" + e.getMessage());

            }
        } else {
            try{
                String patientName = this.deletedPatientInfo;
                String taskName = this.deletedTaskInfo;
                ArrayList<Patient> patientsWithSameName = patientManager.getPatientByName(patientName);
                ArrayList<PatientTask> ToBeDeleted = patientTask.getPatientTask(patientsWithSameName.get(0).getID());
                ArrayList<Task> ToBeDeletedTask = tasks.getTaskByDescription(deletedTaskInfo);
                for (PatientTask patientTask1: ToBeDeleted){
                    if (patientTask1 instanceof EventPatientTask && patientTask1.getTaskID().equals(ToBeDeletedTask.get(0).getID()) && ((EventPatientTask) patientTask1).getStartTime().equals(this.from) && ((EventPatientTask) patientTask1).getEndTime().equals(this.to)) {
                        ui.patientTaskDeleted(patientTask1, patientsWithSameName.get(0), ToBeDeletedTask.get(0));
                        patientTask.deletePatientTask(patientsWithSameName.get(0).getID(),ToBeDeletedTask.get(0).getID(),from,to);
                    }
                    else if (patientTask1 instanceof StandardPatientTask && patientTask1.getTaskID().equals(taskId) && ((StandardPatientTask) patientTask1).getDeadline().equals(this.deadline)){
                        ui.patientTaskDeleted(patientTask1, patientsWithSameName.get(0), ToBeDeletedTask.get(0));
                        patientTask.deletePatientTask(patientsWithSameName.get(0).getID(),ToBeDeletedTask.get(0).getID(),deadline);
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