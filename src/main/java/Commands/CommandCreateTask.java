package Commands;

import Farmio.Farmio;
import FarmioExceptions.FarmioException;
import UserCode.Tasks.Task;
import UserCode.Tasks.TaskList;

//IN PROGRESS

public class CommandCreateTask extends Command {
    private Task task;
    private TaskList taskList;

    public CommandCreateTask(String condition, String action) {
        System.out.println("created new task");
    }

    public CommandCreateTask(String taskType, String condition, String action) {
        System.out.println("created new task");
    }

    public void execute() throws FarmioException {
        try {
            taskList.addTask(task);
        } catch (Exception e) {
            e.getMessage();
        }
    }

    @Override
    public void execute(Farmio farmio) throws FarmioException {

    }
}
