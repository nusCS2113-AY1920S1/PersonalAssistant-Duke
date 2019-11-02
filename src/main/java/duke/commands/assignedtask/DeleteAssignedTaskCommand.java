//@@author WEIFENG-NUSCEG

package duke.commands.assignedtask;

import duke.commands.Command;
import duke.exceptions.DukeException;
import duke.models.tasks.TaskManager;
import duke.util.DukeUi;
import duke.models.patients.PatientManager;
import duke.models.assignedtasks.AssignedTaskManager;
import duke.storages.StorageManager;

/**
 * Represents a command to delete a task assigned to patient through the task's unique id.
 */
public class DeleteAssignedTaskCommand implements Command {
    private int taskUniqueId;


    /**
     * Create a new DeleteAssignedTaskCommand with the deleteInfo received, check if the
     * deletion information follows the correct DeleteAssignedTaskCommand format.
     *
     * @param deleteInfo the unique id of the assigned task to be deleted.
     * @throws DukeException throws exception if the format is wrong.
     */
    public DeleteAssignedTaskCommand(String deleteInfo) throws DukeException {
        super();

        try {
            if (deleteInfo.charAt(0) != '#') {
                throw new DukeException("Invalid format. Please follow: "
                        + "delete assigned task : #<taskUniqueID>");
            }
            taskUniqueId = Integer.parseInt(deleteInfo.substring(1));

        } catch (DukeException e) {
            throw new DukeException("Warning " + e.getMessage());
        }
    }

    /**
     * Run the command with the respect TaskList, UI, and storage, during the execution,
     * during the execution, this method will be checking if the Assigned task to be deleted
     * from the existing list is truly exist.
     *
     * @param assignedTaskManager contains the information between all the tasks and patients.
     * @param taskManager         contains information of all the tasks.
     * @param patientManager      contains information of all the patients.
     * @param ui                  interacts with user.
     * @param storageManager      save the changes in csv file.
     * @throws DukeException if there is error during deleting a assigned task.
     */
    @Override
    public void execute(AssignedTaskManager assignedTaskManager, TaskManager taskManager,
                        PatientManager patientManager, DukeUi dukeUi,
                        StorageManager storageManager)
        throws DukeException {

        try {
            assignedTaskManager.deletePatientTaskByUniqueId(taskUniqueId);
            storageManager.saveAssignedTasks(assignedTaskManager.getAssignTasks());
            dukeUi.patientTaskDeleted(taskUniqueId);
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