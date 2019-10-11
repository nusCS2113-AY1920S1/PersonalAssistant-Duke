package duke.command;

import duke.core.DukeException;
import duke.core.Ui;
import duke.patient.PatientManager;
import duke.storage.PatientStorage;
import duke.storage.TaskStorage;
import duke.task.StandardTask;
import duke.task.TaskManager;

public class AddStandardTaskCommand extends Command{
    private StandardTask newStandardTask;
    public AddStandardTaskCommand(StandardTask newStandardTask) {
        super();
        this.newStandardTask = newStandardTask;
    }

    @Override
    public void execute(TaskManager taskManager, PatientManager patientManager, Ui ui, TaskStorage taskStorage, PatientStorage patientStorage) throws DukeException {
        taskManager.addTask(newStandardTask);
        taskStorage.save(taskManager.getTaskList());
        ui.taskAdded(newStandardTask);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
