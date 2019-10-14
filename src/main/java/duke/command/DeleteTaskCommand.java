package duke.command;

import duke.core.DukeException;
import duke.core.Ui;
import duke.patient.PatientManager;
import duke.storage.PatientStorage;
import duke.storage.PatientTaskStorage;
import duke.storage.TaskStorage;
import duke.relation.PatientTaskList;
import duke.task.Task;
import duke.task.TaskManager;

import java.util.ArrayList;

public class DeleteTaskCommand extends Command{
    private int id;
    private String command;

    public DeleteTaskCommand(String command) {
        this.command = command;
    }

    @Override
    public void execute(PatientTaskList patientTask, TaskManager taskManager, PatientManager patientManager, Ui ui, PatientTaskStorage patientTaskStorage, TaskStorage taskStorage, PatientStorage patientStorage) throws DukeException {
        char firstChar = command.charAt(0);
        if (firstChar == '#') {
            int id;
            try{
                id = Integer.parseInt(command.substring(1, command.length()));
            }catch(Exception e){
                throw new DukeException("Please follow the format 'delete task #<id>'.");
            }

            Task taskToBeDeleted = taskManager.getTask(id);
            boolean toDelete = ui.confirmTaskToBeDeleted(taskToBeDeleted);
            if (toDelete){
                taskManager.deleteTask(id);
                ui.taskDeleted();
                taskStorage.save(taskManager.getTaskList());
            }
        } else {
            ArrayList<Task> tasksWithSameDescription = taskManager.getTaskByDescription(command);
            ui.tasksFoundByDescription(tasksWithSameDescription, command);
            if (tasksWithSameDescription.size() >= 1) {
                int numberChosen = ui.chooseTaskToDelete(tasksWithSameDescription.size());
                if (numberChosen >= 1){
                    boolean toDelete = ui.confirmTaskToBeDeleted(tasksWithSameDescription.get(numberChosen-1));
                    if (toDelete){
                        taskManager.deleteTask(tasksWithSameDescription.get(numberChosen-1).getID());
                        ui.taskDeleted();
                        taskStorage.save(taskManager.getTaskList());
                    }
                }
            }
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
