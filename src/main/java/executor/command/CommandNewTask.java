package executor.command;

import duke.exception.DukeException;
import executor.task.Task;
import executor.task.TaskList;
import executor.task.TaskType;
import interpreter.Parser;
import ui.Ui;
import ui.Wallet;

public class CommandNewTask extends Command {
    protected String userInput;
    protected TaskType taskType;

    /**
     * Constructor for the CommandNewTask subCommand Class.
     * @param userInput The user input from the CLI.
     */
    public CommandNewTask(String userInput, CommandType commandType) {
        this.userInput = userInput;
        this.commandType = CommandType.TASK;
        this.taskType = TaskType.valueOf(commandType.toString());
    }

    @Override
    public void execute(Wallet wallet) {

    }

    @Override
    public void execute(TaskList taskList) {
        try {
            checkForwardSlash(this.userInput);
        } catch (DukeException e) {
            e.printStackTrace();
            return;
        }
        Task newTask = TaskList.createTask(this.taskType, this.userInput);
        taskList.addTask(newTask);
        Ui.dukeSays("I've added "
                + newTask.genTaskDesc()
                + " to your private list("
                + String.valueOf(taskList.getSize())
                + ")."
        );
    }

    /**
     * Throws an exception when there is no '/' in the user input.
     * @param input this is the user's input
     * @throws DukeException this shows the error message and gives the format to follow
     */
    public void checkForwardSlash(String input) throws DukeException {
        if (this.taskType.equals(TaskType.FDURATION)) {
            if (!Parser.containsForwardSlash(input)) {
                throw new DukeException("Check your format!!! Correct format is: fduration <description> / <time>");
            }
        }
    }
}
