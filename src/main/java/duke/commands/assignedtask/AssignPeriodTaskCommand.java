//@@author WEIFENG-NUSCEG

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

/**
 * Represents a command to assign a period task by its task id, to a specific patient with his/her patient id
 * follows by the task details such as the task's starting period, the task's ending period,
 * the task's recursive status and the task's is done status.
 */
public class AssignPeriodTaskCommand implements Command {
    private String[] userInput;

    /**
     * Create a new AssignPeriodTaskCommand with the user input.
     *
     * @param userInput the task information from user input
     */
    public AssignPeriodTaskCommand(String[] userInput) {
        this.userInput = userInput;
    }

    /**
     * Run the command with the respect TaskList, UI, and storage, during the execution, this
     * method will check if the user input is following the correct input format of a assigned
     * deadline task command. This method will also check if the new AssignedTask command is identical
     * with other AssignedTask stored in the AssignedTaskManager.
     *
     * @param assignedTaskManager contains the information between all the tasks and patients.
     * @param taskManager         contains information of all the tasks.
     * @param patientManager      contains information of all the patients.
     * @param dukeUi                  interacts with user.
     * @param storageManager      save the changes in csv file.
     * @throws DukeException if there is error during assigning a period task.
     */
    @Override
    public void execute(AssignedTaskManager assignedTaskManager, TaskManager taskManager, PatientManager patientManager,
                        DukeUi dukeUi, StorageManager storageManager) throws DukeException {

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
        } catch (DukeException e) {
            throw new DukeException(e.getMessage());
        }
    }

    /**
     * Decide whether duke should exist.
     *
     * @return A boolean. True if the command tells Duke to exit, false
     */
    @Override
    public boolean isExit() {
        return false;
    }
}
