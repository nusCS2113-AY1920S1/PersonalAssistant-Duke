package duke.commands;

import duke.exceptions.DukeException;
import duke.util.Ui;
import duke.models.patients.PatientManager;
import duke.models.assignedPatientTasks.AssignedTaskManager;
import duke.storages.StorageManager;
import duke.models.patientTasks.Task;
import duke.models.patientTasks.TaskManager;

public class AddStandardTaskCommand implements Command {
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
    public void execute(AssignedTaskManager patientTask, TaskManager taskList, PatientManager patientList,
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
