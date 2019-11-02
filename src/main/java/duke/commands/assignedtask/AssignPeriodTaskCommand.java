package duke.commands.assignedtask;

import duke.commands.Command;
import duke.exceptions.DukeException;
import duke.models.tasks.TaskManager;
import duke.util.DukeUi;
import duke.models.patients.PatientManager;
import duke.models.assignedtasks.AssignedTaskWithPeriod;
import duke.models.assignedtasks.AssignedTask;
import duke.models.assignedtasks.AssignedTaskManager;
import duke.storages.StorageManager;

public class AssignPeriodTaskCommand implements Command {

    private String command;
    private String[] userInput;
    private AssignedTask newAssignedTask;

    /**
     * .
     *
     * @param userInput .
     * @throws DukeException .
     */
    public AssignPeriodTaskCommand(String[] userInput) throws DukeException {
        this.userInput = userInput;
    }

    /**
     * .
     *
     * @param assignedTaskManager .
     * @param taskManager         .
     * @param patientManager      .
     * @param dukeUi                  .
     * @param storageManager      .
     * @throws DukeException .
     */
    @Override
    public void execute(AssignedTaskManager assignedTaskManager, TaskManager taskManager, PatientManager patientManager,
                        DukeUi dukeUi, StorageManager storageManager) throws DukeException {

        // The followings are written by XUANKUN for temporary use
        try {
            if (userInput.length < 4 || userInput[0].charAt(0) != '#' || userInput[1].charAt(0) != '#') {
                throw new DukeException("Invalid format. Please follow: "
                    + "assign period task: #<patient id> :#<task id> : dd/MM/yyyy HHmm : dd/MM/yyyy HHmm");
            }
            int pid = Integer.parseInt(userInput[0].substring(1));
            int tid = Integer.parseInt(userInput[1].substring(1));
            String stime = userInput[2];
            String etime = userInput[3];
            String type = "period";
            if (!taskManager.doesExist(tid)) {
                throw new DukeException("The task " + tid + " does not exist");
            }
            if (!patientManager.doesExist(pid)) {
                throw new DukeException("The patient " + pid + " does not exist");
            }
            AssignedTask newAssignedTask = new AssignedTaskWithPeriod(pid, tid, stime, etime, type);

            if (!assignedTaskManager.isSameStartEndTimeExist(newAssignedTask)) {
                assignedTaskManager.addPatientTask(newAssignedTask);
                storageManager.saveAssignedTasks(assignedTaskManager.getAssignTasks());
                dukeUi.patientTaskAssigned(newAssignedTask, patientManager.getPatient(pid).getName(),
                        taskManager.getTask(tid).getDescription());
            } else {
                throw new DukeException("A same period task already exists");
            }
        } catch (Exception e) {
            throw new DukeException(e.getMessage());
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