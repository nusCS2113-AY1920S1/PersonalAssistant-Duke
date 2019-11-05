//@@author kkeejjuunn

package duke.commands.task;

import duke.commands.Command;
import duke.exceptions.DukeException;
import duke.models.tasks.Task;
import duke.util.DukeUi;
import duke.models.patients.PatientManager;
import duke.models.assignedtasks.AssignedTaskManager;
import duke.storages.StorageManager;
import duke.models.tasks.TaskManager;

public class AddTaskCommand implements Command {
    private Task newStandardTask;

    /**
     * It receive the task description from parser.
     *
     * @param taskDescription contains the description of the task to be added.
     */
    public AddTaskCommand(String taskDescription) {
        super();
        this.newStandardTask = new Task(taskDescription);

    }

    /**
     * It adds a new task to Dukepital.
     *
     * @param assignedTaskManager contains the information between all the tasks and patients.
     * @param taskManager         contains information of all the tasks.
     * @param patientManager      contains information of all the patients.
     * @param dukeUi              interacts with user.
     * @param storageManager      save the changes in csv file.
     * @throws DukeException      if the task is not added successfully.
     */
    @Override
    public void execute(AssignedTaskManager assignedTaskManager, TaskManager taskManager, PatientManager patientManager,
                        DukeUi dukeUi, StorageManager storageManager) throws DukeException {
        try {
            taskManager.addTask(newStandardTask);
            storageManager.saveTasks(taskManager.getTaskList());
            dukeUi.taskAdded(newStandardTask);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * It terminates the Dukepital.
     *
     * @return false.
     */
    @Override
    public boolean isExit() {
        return false;
    }

}
