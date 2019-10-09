package duke.command;

import duke.core.DukeException;
import duke.core.Ui;
import duke.patient.Patient;
import duke.patient.PatientList;
import duke.storage.PatientStorage;
import duke.storage.TaskStorage;
import duke.task.Task;
import duke.task.StandardTask;
import duke.task.TaskList;

public class AddStandardTaskCommand extends Command{
    private StandardTask newStandardTask;
    public AddStandardTaskCommand(StandardTask newStandardTask) {
        super();
        this.newStandardTask = newStandardTask;
    }

    @Override
    public void execute(TaskList taskList, PatientList patientList, Ui ui, TaskStorage taskStorage, PatientStorage patientStorage) throws DukeException {
        taskList.addTask(newStandardTask);
        taskStorage.save(taskList.getTaskList());
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
