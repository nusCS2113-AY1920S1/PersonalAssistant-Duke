package duke.command;

import duke.core.DukeException;
import duke.core.Ui;
import duke.patient.PatientManager;
import duke.relation.EventPatientTask;
import duke.relation.PatientTask;
import duke.relation.PatientTaskList;
import duke.relation.StandardPatientTask;
import duke.storage.StorageManager;
import duke.task.TaskManager;

public class AssignTaskToPatientCommand extends Command {

    private String command;
    private String[] taskAssignmentInfo;
    private PatientTask newPatientTask;

    /**
     * .
     *
     * @param taskAssignmentInfo .
     * @throws DukeException .
     */
    public AssignTaskToPatientCommand(String[] taskAssignmentInfo) throws DukeException {
        super();
        this.taskAssignmentInfo = taskAssignmentInfo;
    }

    /**
     * .
     *
     * @param patientTaskList    .
     * @param tasksList          .
     * @param patientList        .
     * @param ui                 .
     * @param storageManager .
     * @throws DukeException .
     */
    @Override
    public void execute(PatientTaskList patientTaskList, TaskManager tasksList, PatientManager patientList,
                        Ui ui, StorageManager storageManager) throws DukeException {

        char firstChar = taskAssignmentInfo[1].charAt(0);

        try {
            if (taskAssignmentInfo[0].equals("S")) {
                if (firstChar == '#') {
                    int tempPid = Integer.parseInt(taskAssignmentInfo[1].replace("#","").trim());
                    int tempTid = Integer.parseInt(taskAssignmentInfo[2]);
                    String temptime = taskAssignmentInfo[3];
                    newPatientTask = new StandardPatientTask(tempPid, tempTid, temptime, taskAssignmentInfo[0]);
                } else {
                    int tempPid = patientList.getPatientByName(taskAssignmentInfo[1]).get(0).getID();
                    int tempTid = tasksList.getTaskByDescription(taskAssignmentInfo[2]).get(0).getID();
                    String temptime = taskAssignmentInfo[3];
                    newPatientTask = new StandardPatientTask(tempPid, tempTid, temptime, taskAssignmentInfo[0]);
                }
            } else if (taskAssignmentInfo[0].equals("E")) {
                if (firstChar == '#') {
                    int tempPid = Integer.parseInt(taskAssignmentInfo[1].replace("#","").trim());
                    int tempTid = Integer.parseInt(taskAssignmentInfo[2]);
                    String stime = taskAssignmentInfo[3].split(" to ", 2)[0];
                    String etime = taskAssignmentInfo[3].split(" to ", 2)[1];
                    newPatientTask = new EventPatientTask(tempPid, tempTid, stime, etime,
                            taskAssignmentInfo[0]);
                } else {
                    int tempPid = patientList.getPatientByName(taskAssignmentInfo[1]).get(0).getID();
                    int tempTid = tasksList.getTaskByDescription(taskAssignmentInfo[2]).get(0).getID();
                    String stime = taskAssignmentInfo[3].split(" to ", 2)[0];
                    String etime = taskAssignmentInfo[3].split(" to ", 2)[1];
                    newPatientTask = new EventPatientTask(tempPid, tempTid, stime, etime,
                            taskAssignmentInfo[0]);
                }
            }
        } catch (Exception e) {
            throw new DukeException("You are using the wrong format for the assign command");
        }

        if (patientList.isExist(newPatientTask.getPatientId()) && tasksList.doesExist(newPatientTask.getTaskID())) {
            if (patientTaskList.doesUidExist(newPatientTask.getUid())
                    || patientTaskList.isSameTaskExist(newPatientTask)) {
                throw new DukeException("Either the unique task id is repeated or the same task exists");
            } else {
                patientTaskList.addPatientTask(newPatientTask);
                storageManager.saveAssignedTasks(patientTaskList.fullPatientTaskList());
                ui.patientTaskAssigned(newPatientTask, patientList.getPatient(newPatientTask.getPatientId()).getName(),
                        tasksList.getTask(newPatientTask.getTaskID()).getDescription());
            }
        } else {
            throw new DukeException("Either the patient or the task does not exist in our data record");
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