package duke.commands;

import duke.exceptions.DukeException;
import duke.util.Ui;
import duke.models.patients.PatientManager;
import duke.models.assignedPatientTasks.AssignedTaskManager;
import duke.storages.StorageManager;
import duke.models.patientTasks.Task;
import duke.models.patientTasks.TaskManager;

import java.util.ArrayList;

public class ListTasksCommand implements Command {

    /**
     * .
     *
     * @param patientTask        .
     * @param tasks              .
     * @param patientList        .
     * @param ui                 .
     * @param storageManager .
     * @throws DukeException .
     */
    @Override
    public void execute(AssignedTaskManager patientTask, TaskManager tasks, PatientManager patientList,
                        Ui ui, StorageManager storageManager) {
        ArrayList<Task> list = tasks.getTaskList();
        ui.listAllTasks(list);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
