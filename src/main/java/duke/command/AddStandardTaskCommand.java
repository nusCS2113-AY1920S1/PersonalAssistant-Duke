package duke.command;

import duke.core.DukeException;
import duke.core.Ui;
import duke.patient.PatientManager;
import duke.relation.PatientTaskList;
import duke.storage.StorageManager;
import duke.task.Task;
import duke.task.TaskManager;

public class AddStandardTaskCommand extends Command {
    private Task newStandardTask;

    /**
     * .
     *
     * @param taskDescription .
     */
    public AddStandardTaskCommand(String taskDescription) {
        super();
        this.newStandardTask = new Task(taskDescription);

    }

    /**
     * .
     *
     * @param patientTask        .
     * @param taskList           .
     * @param patientList        .
     * @param ui                 .
     * @param storageManager .
     * @throws DukeException .
     */
    @Override
    public void execute(PatientTaskList patientTask, TaskManager taskList, PatientManager patientList,
                        Ui ui, StorageManager storageManager) throws DukeException {

        taskList.addTask(newStandardTask);
        storageManager.saveTasks(taskList.getTaskList());
        ui.taskAdded(newStandardTask);
    }

    @Override
    public boolean isExit() {
        return false;
    }

}
