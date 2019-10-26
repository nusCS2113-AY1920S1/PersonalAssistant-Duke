package duke.commands.assignedtask;

import duke.commands.Command;
import duke.exceptions.DukeException;
import duke.models.tasks.TaskManager;
import duke.util.Ui;
import duke.models.patients.PatientManager;
import duke.models.assignedtasks.AssignedTaskWithPeriod;
import duke.models.assignedtasks.AssignedTask;
import duke.models.assignedtasks.AssignedTaskManager;
import duke.models.assignedtasks.AssignedTaskWithDate;
import duke.storages.StorageManager;

public class AssignTaskToPatientCommand implements Command {

    private String command;
    private String[] taskAssignmentInfo;
    private AssignedTask newAssignedTask;

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
     * @param assignedTaskManager .
     * @param tasksList           .
     * @param patientList         .
     * @param ui                  .
     * @param storageManager      .
     * @throws DukeException .
     */
    @Override
    public void execute(AssignedTaskManager assignedTaskManager, TaskManager tasksList, PatientManager patientList,
                        Ui ui, StorageManager storageManager) throws DukeException {

        char firstChar = taskAssignmentInfo[0].charAt(0);
        char firstCharOfSecondWord = taskAssignmentInfo[1].charAt(0);

        try {
            if (taskAssignmentInfo.length == 3) {
                if (firstChar == '#' && firstCharOfSecondWord == '#') {
                    int pid = Integer.parseInt(taskAssignmentInfo[0].substring(1));
                    int tid = Integer.parseInt(taskAssignmentInfo[1].substring(1));
                    String time = taskAssignmentInfo[2];
                    String type = "date";
                    newAssignedTask = new AssignedTaskWithDate(pid, tid, time, type);
                } else {
                    throw new DukeException("You are using the wrong format for the assign command!");
                }
            } else if (taskAssignmentInfo.length == 4) {
                if (firstChar == '#') {
                    int pid = Integer.parseInt(taskAssignmentInfo[0].substring(1));
                    int tid = Integer.parseInt(taskAssignmentInfo[1].substring(1));
                    String stime = taskAssignmentInfo[2];
                    String etime = taskAssignmentInfo[3];
                    String type = "period";
                    newAssignedTask = new AssignedTaskWithPeriod(pid, tid, stime, etime, type);
                } else {
                    throw new DukeException("You are using the wrong format for the assign command!");
                }
            } else {
                throw new DukeException("You are using the wrong format for the assign command!");
            }
        } catch (DukeException e) {
            throw e;
        }

        /*
        if (patientList.isExist(newAssignedTask.getPid()) && tasksList.doesExist(newAssignedTask.getPid())) {
            if (assignedTaskManager.doesUidExist(newAssignedTask.getUuid())
                    || assignedTaskManager.isSameTaskExist(newAssignedTask)) {
                throw new DukeException("Either the unique task id is repeated or the same task exists");
            } else {
                assignedTaskManager.addAssignedTask(newAssignedTask);
                storageManager.saveAssignedTasks(assignedTaskManager.getAssignTasks());
                ui.patientTaskAssigned(newAssignedTask, patientList.getPatient(newAssignedTask.getPid()).getName(),
                        tasksList.getTask(newAssignedTask.getPatientTaskId()).getDescription());
            }
        } else {
            throw new DukeException("Either the patient or the task does not exist in our data record");
        }
        */
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