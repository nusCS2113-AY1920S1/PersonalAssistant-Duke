package duke.command;

import duke.core.DukeException;
import duke.core.ShortCutter;
import duke.core.Ui;
import duke.patient.PatientManager;
import duke.relation.PatientTaskList;
import duke.statistic.Counter;
import duke.storage.StorageManager;
import duke.task.TaskManager;


public class DukeCommand extends Command {

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

        Counter counter = new Counter(storageManager.loadCommandFrequency());
        ShortCutter shortCutter = new ShortCutter(counter, ui);
        shortCutter.runShortCut().execute(patientTask, tasks, patientManager, ui, storageManager);

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
