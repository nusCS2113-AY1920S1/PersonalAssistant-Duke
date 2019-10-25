package duke.command;

import duke.MementoPattern.Memento;
import duke.MementoPattern.MementoManager;
import duke.core.DukeException;
import duke.core.Ui;
import duke.patient.PatientManager;
import duke.relation.PatientTaskList;
import duke.storage.StorageManager;
import duke.task.TaskManager;

public class UndoCommand extends Command{

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
    public void execute(PatientTaskList patientTask, TaskManager tasks, PatientManager patientManager,
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
