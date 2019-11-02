//@@author WEIFENG-NUSCEG

package duke.commands.functional;

import duke.commands.Command;
import duke.exceptions.DukeException;
import duke.models.tasks.TaskManager;
import duke.util.DukeUi;
import duke.models.patients.PatientManager;
import duke.models.assignedtasks.AssignedTaskManager;
import duke.storages.StorageManager;

/**
 * Represents a command to undo a last step. The command.UndoCommand class
 * extends from the Command class for the user to undo the last operation
 */
public class UndoCommand implements Command {

    /**
     * To save the internal state of the objects retrieved from Memento into local CSV files
     * run the command with the respect TaskList, UI, and storage.
     *
     * @param patientTask contains the information between all the tasks and patients.
     * @param tasks       contains information of all the tasks.
     * @param patientManager      contains information of all the patients.
     * @param dukeUi            interacts with user.
     * @param storageManager      save the changes in csv file.
     * @throws DukeException if there is error during saving the internal states.
     */
    @Override
    public void execute(AssignedTaskManager patientTask, TaskManager tasks, PatientManager patientManager,
                        DukeUi dukeUi, StorageManager storageManager) throws DukeException {
        storageManager.savePatients(patientManager.getPatientList());
        storageManager.saveTasks(tasks.getTaskList());
        storageManager.saveAssignedTasks(patientTask.getAssignTasks());
        dukeUi.showUndoSuccess();
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
