package duke.commands.functional;

import duke.commands.Command;
import duke.exceptions.DukeException;
import duke.util.Ui;
import duke.models.patients.PatientManager;
import duke.models.assignedtasks.AssignedTaskManager;
import duke.storages.StorageManager;
import duke.models.tasks.TaskManager;

public class UndoCommand implements Command {

    /**
     * .
     *
     * @param patientTask    .
     * @param tasks          .
     * @param patientManager .
     * @param ui             .
     * @param storageManager .
     * @throws DukeException .
     */
    @Override
    public void execute(AssignedTaskManager patientTask, TaskManager tasks, PatientManager patientManager,
                        Ui ui, StorageManager storageManager) throws DukeException {
        storageManager.savePatients(patientManager.getPatientList());
        storageManager.saveTasks(tasks.getTaskList());
        storageManager.saveAssignedTasks(patientTask.fullPatientTaskList());
        ui.showUndoSuccess();
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
