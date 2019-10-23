package executor.command;

import executor.task.Task;
import executor.task.TaskList;
import interpreter.Parser;
import ui.Ui;
import ui.Wallet;

public class CommandQueue extends Command {
    protected String userInput;

    /**
     * Constructor for CommandQueue subCommand Class.
     * @param userInput The user input from the CLI
     */
    public CommandQueue(String userInput) {
        this.userInput = userInput;
        this.commandType = CommandType.QUEUE;
    }

    @Override
    public void execute(Wallet wallet) {

    }

    @Override
    public void execute(TaskList taskList) {
        TaskList throwaway = new TaskList();
        String[] parsedInput = Parser.removeStr(this.commandType.toString(), this.userInput)
                .split(" ", 2);
        int mainTaskIndex = Integer.parseInt(parsedInput[0]) - 1;
        Task mainTask = taskList.getList().get(mainTaskIndex);
        String taskString = parsedInput[1].trim();
        CommandType commandType = Parser.parseForCommandType(taskString);
        Command createNewTaskCommand = Executor.createCommand(commandType, taskString);
        if (createNewTaskCommand.commandType != CommandType.TASK) {
            Ui.dukeSays("No Task detected after 'Queue'.");
            return;
        }
        initializeQueue(mainTask);
        createNewTaskCommand.execute(mainTask.getQueuedTasks());
    }

    /**
     * Initializes the Queue if it hasn't been initialized.
     * @param task The task to initialize the Queue in.
     */
    private void initializeQueue(Task task) {
        if (task.isQueuedTasks()) {
            return;
        }
        task.setQueuedTasks(new TaskList());
    }
}
