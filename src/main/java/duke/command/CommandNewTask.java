package duke.command;

import duke.task.Task;
import duke.task.TaskList;
import duke.task.TaskType;
import duke.worker.Parser;
import duke.worker.Ui;

public class CommandNewTask extends Command {
    protected String userInput;
    protected TaskType taskType;

    // Constructor
    public CommandNewTask(String userInput) {
        this.taskType = Parser.parseTaskType(userInput);
        this.userInput = userInput;
    }

    @Override
    public void execute(TaskList taskList) {
        Task newTask = TaskList.createTask(this.taskType, this.userInput);
        taskList.addTask(newTask);
        Ui.dukeSays("I've added "
                + newTask.genTaskDesc()
                + " to your private list("
                + String.valueOf(taskList.getSize())
                + ")."
        );
    }
}
