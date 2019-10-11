package duke.command;

import duke.core.DukeException;
import duke.core.Ui;
import duke.patient.PatientList;
import duke.storage.PatientStorage;
import duke.storage.PatientTaskStorage;
import duke.storage.TaskStorage;
import duke.relation.PatientTaskList;
import duke.task.Task;
import duke.task.TaskList;

import java.util.ArrayList;

public class ListTasksCommand extends Command {
    @Override
    public void execute(PatientTaskList patientTask, TaskList tasks, PatientList patientList, Ui ui, PatientTaskStorage patientTaskStorage, TaskStorage taskStorage, PatientStorage patientStorage) throws DukeException {
        ArrayList<Task> list = tasks.getTaskList();
        ui.listAllTasks(list);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
