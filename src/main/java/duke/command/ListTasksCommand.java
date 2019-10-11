package duke.command;

import duke.core.DukeException;
import duke.core.Ui;
import duke.patient.PatientManager;
import duke.storage.PatientStorage;
import duke.storage.TaskStorage;
import duke.task.Task;
import duke.task.TaskManager;

import java.util.ArrayList;

public class ListTasksCommand extends Command {
    @Override
    public void execute(TaskManager tasks, PatientManager patientManager, Ui ui, TaskStorage taskStorage, PatientStorage patientStorage) throws DukeException {
        ArrayList<Task> list = tasks.getTaskList();
        ui.listAllTasks(list);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
