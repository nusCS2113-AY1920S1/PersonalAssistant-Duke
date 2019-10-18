package duke.command;

import duke.core.DukeException;
import duke.core.Ui;
import duke.task.Task;
import duke.patient.PatientManager;
import duke.storage.PatientStorage;
import duke.storage.PatientTaskStorage;
import duke.storage.TaskStorage;
import duke.relation.PatientTaskList;
import duke.task.TaskManager;

public class UpdateTaskCommand extends Command{
    private String command;

    public UpdateTaskCommand(String command) { this.command = command; }

    @Override
    public void execute(PatientTaskList patientTask, TaskManager taskManager, PatientManager patientManager, Ui ui, PatientTaskStorage patientTaskStorage, TaskStorage taskStorage, PatientStorage patientStorage) throws DukeException {
        String[] tempCommand = command.split(" ", 3);
        char firstChar = tempCommand[0].charAt(0);
        if (firstChar == '#'){
            int id;
            try {
                id = Integer.parseInt(tempCommand[0].substring(1, tempCommand[0].length()));
                Task taskToBeUpdated = taskManager.getTask(id);
                if (tempCommand[1].toLowerCase().equals("description")) {
                    taskToBeUpdated.setDescription(tempCommand[2]);
                }
                else {
                    throw new DukeException("You can only update 'Description' of the task");
                }

                taskStorage.save(taskManager.getTaskList());

                ui.showUpdatedSuccessfully();
                ui.showTaskInfo(taskToBeUpdated);
            }catch(Exception e) {
                throw new DukeException("Please follow the format 'update patient #<id> <Name/NRIC/Room> <new information>'.");
            }

        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
