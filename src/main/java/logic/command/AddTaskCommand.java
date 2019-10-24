package logic.command;
import model.Model;
import model.task.Task;
import model.task.Name;

public class AddTaskCommand extends Command {
    public static final String COMMAND_WORD = "task";
    private String taskName;

    public AddTaskCommand(String arguments) {
        taskName = arguments;
    }

    @Override
    public CommandOutput execute(Model model) {
        Name name = new Name(taskName);
        Task newTask = new Task(name);
        model.addTask(newTask);
        return new CommandOutput("you have created a new Task: " + newTask.getName().toString());
    }
}
