//@@kkeejjuunn

package duke.command;

import duke.core.DukeException;
import duke.core.Ui;
import duke.patient.PatientManager;
import duke.relation.PatientTaskList;
import duke.storage.StorageManager;
import duke.task.Task;
import duke.task.TaskManager;

public class UpdateTaskCommand implements Command {
    private String[] command;

    /**
     * .
     *
     * @param command .
     */
    public UpdateTaskCommand(String[] command) {
        this.command = command;
    }

    /**
     * .
     *
     * @param patientTask        .
     * @param taskManager        .
     * @param patientManager     .
     * @param ui                 .
     * @param storageManager .
     * @throws DukeException .
     */
    @Override
    public void execute(PatientTaskList patientTask, TaskManager taskManager, PatientManager patientManager,
                        Ui ui, StorageManager storageManager) throws DukeException {
        char firstChar = command[0].charAt(0);
        if (firstChar == '#') {
            int id;
            try {
                id = Integer.parseInt(command[0].substring(1, command[0].length()));
                Task taskToBeUpdated = taskManager.getTask(id);
                if (command[1].toLowerCase().equals("description")) {
                    taskToBeUpdated.setDescription(command[2]);
                } else {
                    throw new DukeException("You can only update 'Description' of the task");
                }

                storageManager.saveTasks(taskManager.getTaskList());
                ui.showUpdatedSuccessfully();
                ui.showTaskInfo(taskToBeUpdated);
            } catch (Exception e) {
                throw new DukeException(
                        "Please follow the format 'update task :#<id> :description :<new description>'.");
            }

        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
