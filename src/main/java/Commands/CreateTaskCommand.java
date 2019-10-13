package Commands;

import FarmioExceptions.FarmioException;
import UserCode.Tasks.Task;
import UserCode.Tasks.TaskList;

public class CreateTaskCommand extends Command {
    private Task task;
    private TaskList taskList;

    public CreateTaskCommand(Task task, TaskList tasks) {
        this.task = task;
        this.taskList = tasks;
    }

    @Override
    public void execute() throws FarmioException {
        try {
            tasks.addTask(task);
        } catch (Exception e) {
            e.getMessage();
        }
    }
}
