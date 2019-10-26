package duke.commands.assignedtask;

import duke.commands.Command;
import duke.exceptions.DukeException;
import duke.util.Ui;
import duke.models.patients.PatientManager;
import duke.models.assignedtasks.AssignedTaskWithPeriod;
import duke.models.assignedtasks.AssignedTask;
import duke.models.assignedtasks.AssignedTaskManager;
import duke.models.assignedtasks.AssignedTaskWithDate;
import duke.storages.StorageManager;
import duke.models.tasks.TaskManager;

public class AssignTaskToPatientCommand implements Command {

    private String command;
    private String[] taskAssignmentInfo;
    private AssignedTask newPatientTask;

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
     * @param assignedTaskManager    .
     * @param tasksList          .
     * @param patientList        .
     * @param ui                 .
     * @param storageManager .
     * @throws DukeException .
     */
    @Override
    public void execute(AssignedTaskManager assignedTaskManager, TaskManager tasksList, PatientManager patientList,
                        Ui ui, StorageManager storageManager) throws DukeException {

        char firstChar = taskAssignmentInfo[0].charAt(0);

        try {
            if (taskAssignmentInfo.length == 3) {
                if (firstChar == '#') {
                    int pid = Integer.parseInt(taskAssignmentInfo[0].substring(1));
                    int tid = Integer.parseInt(taskAssignmentInfo[1]);
                    String time = taskAssignmentInfo[2];
                    String type = "S";
                    newPatientTask = new AssignedTaskWithDate(pid, tid, time, type);
                } else {
                    int pid = patientList.getPatientByName(taskAssignmentInfo[0]).get(0).getId();
                    int tid = tasksList.getTaskByDescription(taskAssignmentInfo[1]).get(0).getID();
                    String time = taskAssignmentInfo[2];
                    String type = "S";
                    newPatientTask = new AssignedTaskWithDate(pid, tid, time, type);
                }
            } else if (taskAssignmentInfo.length == 4) {
                if (firstChar == '#') {
                    int pid = Integer.parseInt(taskAssignmentInfo[0].substring(1));
                    int tid = Integer.parseInt(taskAssignmentInfo[1]);
                    String stime = taskAssignmentInfo[2];
                    String etime = taskAssignmentInfo[3];
                    String type = "E";
                    newPatientTask = new AssignedTaskWithPeriod(pid, tid, stime, etime, type);
                } else {
                    int pid = patientList.getPatientByName(taskAssignmentInfo[0]).get(0).getId();
                    int tid = tasksList.getTaskByDescription(taskAssignmentInfo[1]).get(0).getID();
                    String stime = taskAssignmentInfo[2];
                    String etime = taskAssignmentInfo[3];
                    String type = "E";
                    newPatientTask = new AssignedTaskWithPeriod(pid, tid, stime, etime, type);
                }
            } else {
                throw new DukeException("You are using the wrong format for the assign command!");
            }
        } catch (DukeException e) {
            throw new DukeException("You are using the wrong format for the assign command!");
        }

        if (patientList.isExist(newPatientTask.getPatientId()) && tasksList.doesExist(newPatientTask.getTaskID())) {
            if (assignedTaskManager.doesUidExist(newPatientTask.getUid())
                    || assignedTaskManager.isSameTaskExist(newPatientTask)) {
                throw new DukeException("Either the unique task id is repeated or the same task exists");
            } else {
                assignedTaskManager.addPatientTask(newPatientTask);
                storageManager.saveAssignedTasks(assignedTaskManager.fullPatientTaskList());
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